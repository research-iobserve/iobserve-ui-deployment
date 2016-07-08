import Ember from 'ember';

export default Ember.Service.extend({
    shouldClose: false,
    init() {
        this._super(...arguments);
        this.debug('session', this.get('systemId'));
    },
    connect(systemId) {
        this.set('shouldClose', false);

        this.debug('setting up websocket', systemId);
        const socket = new WebSocket(`ws://localhost:8080/v1/changelogstream/${systemId}`);
        this.set('socket', socket);

        socket.onopen = this.get('events.onOpen').bind(this);
        socket.onerror = this.get('events.onError').bind(this);
        socket.onmessage = this.get('events.onMessage').bind(this);

        // automatically reconnect
        socket.onclose = () => {
            if(!this.get('shouldClose')) {
                this.debug('connection lost, reconnecting!');
                this.set('reconnectionTimeout', setTimeout(() => {
                    this.connect(systemId);
                    this.set('reconnectionTimeout', null);
                }, 500));
            }
        };

        // close socket connection when the user closes the window/tab or nagivates to a different website
        window.onbeforeunload = this.get('disconnect').bind(this);
    },
    disconnect() {
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
            const changelogJson = message.data;
            this.debug('new changelog received', changelogJson);
            try {
                const changelog = JSON.parse(changelogJson);
                this.debug('changelog converted', changelog);
            } catch (e) {
                console.error('could not parse changelog json', e, changelogJson);
            }
        }
    }
});
