import Ember from 'ember';
import ENV from 'iobserve-ui/config/environment';

export default Ember.Service.extend({
    changelogQueue: Ember.inject.service(),
    shouldClose: false,
    init() {
        this._super(...arguments);
        this.debug('session', this.get('systemId'));
    },
    connect(systemId) {
        this.set('shouldClose', false);

        this.debug('setting up websocket', systemId);
        const socket = new WebSocket(`${ENV.APP.WEBSOCKET_ROOT}/v1/changelogstream/${systemId}`);
        this.set('socket', socket);

        socket.onopen = this.get('events.onOpen').bind(this);
        socket.onerror = this.get('events.onError').bind(this);
        socket.onmessage = this.get('events.onMessage').bind(this);

        // automatically reconnect
        if(ENV.APP.WEBSOCKET_RECONNECT) {
            socket.onclose = () => {
                if(!this.get('shouldClose')) {
                    this.debug('connection lost, reconnecting!');
                    this.set('reconnectionTimeout', setTimeout(() => {
                        this.connect(systemId);
                        this.set('reconnectionTimeout', null);
                    }, 500));
                }
            };
        }

        // close socket connection when the user closes the window/tab or nagivates to a different website
        window.onbeforeunload = this.get('disconnect').bind(this);
    },
    disconnect() {
        this.get('changelogQueue').reset();
        this.debug('disconnect');
        this.set('shouldClose', true);
        this.get('socket').close();

        // just in case it disconnected right before disconnect() was called.
        clearTimeout(this.get('reconnectionTimeout'));
    },
    events: {
        onError(err) {
            this.debug('socket connection encountered an error', err);
        },
        onOpen() {
            this.debug('connection established');
        },
        onMessage(message) {
            const changelogsJson = message.data;
            this.debug('new changelogs received', changelogsJson);
            try {
                const changelogs = JSON.parse(changelogsJson);
                this.get('changelogQueue').append(changelogs);
            } catch (e) {
                console.error('could not parse changelogs json', e, changelogsJson);
            }
        }
    }
});
