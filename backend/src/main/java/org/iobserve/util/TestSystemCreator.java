package org.iobserve.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;

import org.iobserve.models.Communication;
import org.iobserve.models.CommunicationInstance;
import org.iobserve.models.ModelType;
import org.iobserve.models.Node;
import org.iobserve.models.NodeGroup;
import org.iobserve.models.Service;
import org.iobserve.models.ServiceInstance;
import org.iobserve.models.System;
import org.iobserve.models.UserGroup;
import org.iobserve.models.util.Measurable;
import org.iobserve.models.util.RevisionedBean;
import org.iobserve.models.util.SeriesElement;
import org.iobserve.models.util.Status;
import org.iobserve.models.util.StatusInfo;
import org.iobserve.models.util.TimeSeries;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
public class TestSystemCreator {
    private static final String REST = "REST";
    private static final String TCP = "TCP/IP";

    private final Random random;
    private final EntityManagerFactory entityManagerFactory;

    @Inject
    public TestSystemCreator(final EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.random = new Random();
    }

    @Transactional
    public void createTestSystem(final String systemId) {
        final EntityManager entityManager = this.entityManagerFactory.createEntityManager();
        final System oldSystem = entityManager.find(System.class, systemId);
        if (oldSystem != null) {
            final EntityTransaction tx = entityManager.getTransaction();
            tx.begin(); // fehler zurückgeben
            entityManager.remove(oldSystem);
            tx.commit();
        }
        final System system = new System(); // system anhand der dto erstellen
        system.setId(systemId);
        system.setName("Test System");

        // nodes
        final Node webFrontendNode = this.createNode(systemId, "node", 1, "Web Frontend", "web-frontend", "172.17.0.2",
                20, 40);
        final Node storeServiceNode = this.createNode(systemId, "node", 2, "Store Service", "store1", "172.17.0.4", 40,
                70);
        final Node enterpriseServiceNode = this.createNode(systemId, "node", 3, "Enterprise Service", "enterprise",
                "172.17.0.5", 2, 3);
        final Node registryServiceNode = this.createNode(systemId, "node", 4, "Registry Service", "register",
                "172.17.0.3", 0, 1);
        final Node serviceAdapterNode = this.createNode(systemId, "node", 5, "Service Adapter", "adapter", "172.17.0.6",
                2, 7);
        final Node databaseNode = this.createNode(systemId, "node", 6, "Data Center", "psql1", "172.17.0.8", 1, 2);

        // node group - has both nodes
        final NodeGroup nodeGroup1 = this.prepareDummy(new NodeGroup(), systemId, "nodeGroup", 1);
        nodeGroup1.setName("CoCoME");
        nodeGroup1.setNodes(Arrays.asList(webFrontendNode, storeServiceNode, enterpriseServiceNode, registryServiceNode,
                serviceAdapterNode, databaseNode));

        webFrontendNode.setNodeGroup(nodeGroup1);
        storeServiceNode.setNodeGroup(nodeGroup1);
        enterpriseServiceNode.setNodeGroup(nodeGroup1);
        registryServiceNode.setNodeGroup(nodeGroup1);
        serviceAdapterNode.setNodeGroup(nodeGroup1);
        databaseNode.setNodeGroup(nodeGroup1);

        // service instances
        /** webfrontend */
        final ServiceInstance cloudWebInstance = this.createServiceInstance(webFrontendNode, "org.cocome.cloud .web",
                20, 40, systemId, 1);
        /** store service */
        final ServiceInstance storeCashdeskInstance = this.createServiceInstance(storeServiceNode,
                "org.cocome.cloud .logic.webservice .cashdeskline.cashdesk", 5, 8, systemId, 2);
        final ServiceInstance storeTradingsystemCashdesklineInstance = this.createServiceInstance(storeServiceNode,
                "org.cocome .tradingsystem .cashdeskline", 7, 9, systemId, 3);
        final ServiceInstance storeTradingsystemInventoryInstance = this.createServiceInstance(storeServiceNode,
                "org.cocome .tradingsystem .inventory", 92, 95, systemId, 4);
        storeTradingsystemInventoryInstance.setStatus(Status.WARNING);
        /** registry service */
        final ServiceInstance registryServiceInstance = this.createServiceInstance(registryServiceNode,
                "org.cocome.cloud .registry.service", 0, 1, systemId, 5);
        /** enterprise service */
        final ServiceInstance enterpriseInventoryInstance = this.createServiceInstance(enterpriseServiceNode,
                "org.cocome .tradingsystem .inventory", 1, 2, systemId, 6);
        final ServiceInstance enterpriseServiceInstance = this.createServiceInstance(enterpriseServiceNode,
                "org.cocome.cloud .webservice .enterpriseservice", 0, 1, systemId, 7);
        /** service adapter */
        final ServiceInstance serviceAdapterInstance = this.createServiceInstance(serviceAdapterNode,
                "org.cocome.cloud .sa.serviceprovider", 2, 7, systemId, 8);
        /** database */
        final ServiceInstance databaseInstance = this.createServiceInstance(databaseNode, "Postgres", 1, 2, systemId,
                9);

        // create services
        /** web frontend */
        final Service cloudWebservice = this.createService("org.cocome.cloud.web", "", systemId, 1);

        /** store service */
        final Service storeCashdeskService = this
                .createService("org.cocome.cloud .logic.webservice .cashdeskline.cashdesk", "", systemId, 2);
        final Service storeTradingsystemCashdesklineService = this
                .createService("org.cocome .tradingsystem .cashdeskline", "", systemId, 3);
        final Service storeTradingsystemInventoryService = this.createService("org.cocome .tradingsystem .inventory",
                "", systemId, 4);

        /** registry service */
        final Service registryService = this.createService("org.cocome.cloud .registry.service", "", systemId, 5);

        /** enterprise service */
        final Service enterpriseInventoryService = this.createService("org.cocome .tradingsystem .inventory", "",
                systemId, 6);
        final Service enterpriseService = this.createService("org.cocome.cloud .webservice .enterpriseservice", "",
                systemId, 7);

        final List<Service> services = new ArrayList<>();
        services.add(cloudWebservice);
        services.add(storeCashdeskService);
        /** usergroup */
        final UserGroup userGroup = this.createUserGroup(services, systemId, 10);

        /** service adapter */
        final Service serviceAdapter = this.createService("org.cocome.cloud .sa.serviceprovider", "", systemId, 8);

        /** database */
        final Service databaseService = this.createService("Postgres", "", systemId, 9);

        // link service to instances and backwards
        this.link(cloudWebInstance, cloudWebservice);

        this.link(storeCashdeskInstance, storeCashdeskService);
        this.link(storeTradingsystemCashdesklineInstance, storeTradingsystemCashdesklineService);
        this.link(storeTradingsystemInventoryInstance, storeTradingsystemInventoryService);

        this.link(registryServiceInstance, registryService);

        this.link(enterpriseInventoryInstance, enterpriseInventoryService);
        this.link(enterpriseServiceInstance, enterpriseService);

        this.link(serviceAdapterInstance, serviceAdapter);

        this.link(databaseInstance, databaseService);

        /** write services back to node */
        webFrontendNode.setServices(Collections.singletonList(cloudWebInstance));

        storeServiceNode.setServices(Collections.singletonList(storeCashdeskInstance));
        storeServiceNode.setServices(Collections.singletonList(storeTradingsystemCashdesklineInstance));
        storeServiceNode.setServices(Collections.singletonList(storeTradingsystemInventoryInstance));

        registryServiceNode.setServices(Collections.singletonList(registryServiceInstance));

        enterpriseServiceNode.setServices(Collections.singletonList(enterpriseInventoryInstance));
        enterpriseServiceNode.setServices(Collections.singletonList(enterpriseServiceInstance));

        serviceAdapterNode.setServices(Collections.singletonList(serviceAdapterInstance));

        databaseNode.setServices(Collections.singletonList(databaseInstance));

        /** communication instances - duplex communication */
        final CommunicationInstance comInstance1 = this.createCommunication(cloudWebInstance, storeCashdeskInstance,
                systemId, 1);

        final CommunicationInstance comInstance2 = this.createCommunication(storeCashdeskInstance,
                storeTradingsystemCashdesklineInstance, systemId, 2);
        final CommunicationInstance comInstance3 = this.createCommunication(storeTradingsystemCashdesklineInstance,
                storeTradingsystemInventoryInstance, systemId, 3);
        final CommunicationInstance comInstance4 = this.createCommunication(storeTradingsystemInventoryInstance,
                serviceAdapterInstance, systemId, 4);
        final CommunicationInstance comInstance5 = this.createCommunication(storeTradingsystemInventoryInstance,
                enterpriseInventoryInstance, systemId, 5);

        final CommunicationInstance comInstance6 = this.createCommunication(serviceAdapterInstance, databaseInstance,
                systemId, 6);

        final CommunicationInstance comInstance7 = this.createCommunication(enterpriseInventoryInstance,
                enterpriseServiceInstance, systemId, 7);
        final CommunicationInstance comInstance8 = this.createCommunication(enterpriseServiceInstance,
                serviceAdapterInstance, systemId, 8);

        final CommunicationInstance comInstance9 = this.createCommunication(storeTradingsystemInventoryInstance,
                registryServiceInstance, systemId, 9);
        final CommunicationInstance comInstance10 = this.createCommunication(enterpriseServiceInstance,
                registryServiceInstance, systemId, 10);

        // communications - duplex
        final Communication communication1 = this.createCommunication(cloudWebservice, storeCashdeskService,
                TestSystemCreator.REST, comInstance1, systemId, 1);

        final Communication communication2 = this.createCommunication(storeCashdeskService,
                storeTradingsystemCashdesklineService, TestSystemCreator.TCP, comInstance2, systemId, 2);
        final Communication communication3 = this.createCommunication(storeTradingsystemCashdesklineService,
                storeTradingsystemInventoryService, TestSystemCreator.TCP, comInstance3, systemId, 3);
        final Communication communication4 = this.createCommunication(storeTradingsystemInventoryService,
                serviceAdapter, TestSystemCreator.TCP, comInstance4, systemId, 4);
        final Communication communication5 = this.createCommunication(storeTradingsystemInventoryService,
                serviceAdapter, TestSystemCreator.TCP, comInstance5, systemId, 5);

        final Communication communication6 = this.createCommunication(serviceAdapter, databaseService,
                TestSystemCreator.TCP, comInstance6, systemId, 6);

        final Communication communication7 = this.createCommunication(enterpriseInventoryService, enterpriseService,
                TestSystemCreator.TCP, comInstance7, systemId, 7);
        final Communication communication8 = this.createCommunication(enterpriseService, serviceAdapter,
                TestSystemCreator.TCP, comInstance8, systemId, 8);

        final Communication communication9 = this.createCommunication(storeTradingsystemInventoryService,
                registryService, TestSystemCreator.TCP, comInstance9, systemId, 9);
        final Communication communication10 = this.createCommunication(enterpriseService, registryService,
                TestSystemCreator.TCP, comInstance10, systemId, 10);

        system.setCommunications(Arrays.asList(communication1, communication2, communication3, communication4,
                communication5, communication6, communication7, communication8, communication9, communication10));
        system.setServices(Arrays.asList(cloudWebservice, storeCashdeskService, storeTradingsystemCashdesklineService,
                storeTradingsystemInventoryService, enterpriseInventoryService, enterpriseService, registryService,
                serviceAdapter, databaseService));
        system.setNodeGroups(Collections.singletonList(nodeGroup1));

        final EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(system); // saves all thanks to cascading
        tx.commit();
        entityManager.close();
    }

    /**
     *
     * @param source
     * @param target
     * @param technology
     * @param systemId
     * @param counter
     * @return
     */
    private Communication createCommunication(final Service source, final Service target, final String technology,
            final CommunicationInstance communicationInstance, final String systemId, final int counter) {
        final Communication communication = this.prepareDummy(new Communication(), systemId, "communication", counter);
        communication.setSource(source);
        communication.setTarget(target);
        communication.setTechnology(technology);

        communication.setInstances(Collections.singletonList(communicationInstance));

        communicationInstance.setCommunication(communication);
        communicationInstance.addStatusInformation(
                this.createInfo(communication, "connections", this.randomNumber(5, 30).toString()));
        communicationInstance.setWorkload(ThreadLocalRandom.current().nextLong(5, 30));
        return communication;
    }

    private Integer randomNumber(final int i, final int j) {
        return (this.random.nextInt() % (j - i)) + i;
    }

    /**
     *
     * @param source
     * @param target
     * @param systemId
     * @param counter
     */
    private CommunicationInstance createCommunication(final ServiceInstance source, final ServiceInstance target,
            final String systemId, final int counter) {
        final CommunicationInstance communicationInstance = this.prepareDummy(new CommunicationInstance(), systemId,
                "communicationInstance", counter);
        communicationInstance.setSource(source);
        communicationInstance.setTarget(target);

        return communicationInstance;
    }

    /**
     * Link service to service instance.
     *
     * @param instance
     * @param service
     */
    private void link(final ServiceInstance instance, final Service service) {
        instance.setService(service);
        service.setInstances(Collections.singletonList(instance));
    }

    private Service createService(final String name, final String description, final String systemId,
            final int counter) {
        final Service service = this.prepareDummy(new Service(), systemId, "service", counter);
        service.setName(name);
        service.setDescription(description);
        this.generateSeries(service, "Requests", "Number of Requests", 5, 8);

        return service;
    }

    /**
     * Create an instance of a service.
     *
     * @param node
     *            the node where the service is deployed
     * @param instanceName
     *            name of the instance
     * @param systemId
     *            system id
     * @param counter
     *            counter
     *
     * @return the service instance
     */
    private ServiceInstance createServiceInstance(final Node node, final String instanceName, final int low,
            final int high, final String systemId, final int counter) {
        final ServiceInstance serviceInstance = this.prepareDummy(new ServiceInstance(instanceName), systemId,
                "serviceInstance", counter);
        serviceInstance.setNode(node);
        this.generateSeries(serviceInstance, "Requests", "Number of Requests", low * 15, high * 15);
        return serviceInstance;
    }

    /**
     * Create a usergroup
     */
    private UserGroup createUserGroup(final List<Service> services, final String systemId, final int counter) {

        final UserGroup userGroup = this.prepareDummy(new UserGroup("name", services), systemId, "usergroup", counter);

        return userGroup;
    }

    /**
     * Create one service node.
     *
     * @param systemId
     *            the id of the software system this node belongs to
     * @param prefix
     * @param counter
     * @param name
     *            name of the node to be displayed
     * @param hostname
     *            hostname of then node
     * @param ip
     *            ip address of the node
     *
     * @return returns a node for the visualization
     */
    private Node createNode(final String systemId, final String prefix, final int counter, final String name,
            final String hostname, final String ip, final int low, final int high) {
        final Node node = this.prepareDummy(new Node(), systemId, prefix, counter);
        node.setName(name);
        node.setHostname(hostname);
        node.setIp(ip);
        this.generateSeries(node, "Utilization", "%", low, high);

        return node;
    }

    /**
     * Configure a given object.
     *
     * @param bean
     *            the object
     * @param systemId
     *            the system id
     * @param prefix
     *            the common prefix for this type of object
     * @param counter
     *            the counter id.
     *
     * @return returns the configured object
     */
    private <Bean extends RevisionedBean> Bean prepareDummy(final Bean bean, final String systemId, final String prefix,
            final int counter) {
        bean.setId(this.generateTestId(systemId, prefix, counter));
        bean.setSystemId(systemId);
        bean.setChangelogSequence(0L);
        bean.setLastUpdate(new Date());
        bean.setRevisionNumber(0L);
        return bean;
    }

    private String generateTestId(final String systemId, final String prefix, final int counter) {
        return "test-" + systemId + "-" + prefix + "-" + counter;
    }

    private StatusInfo createInfo(final Measurable parent, final String key, final String value) {
        final StatusInfo info = new StatusInfo();
        info.setId(this.generateId());
        info.setTimestamp(new Date().getTime());
        info.setKey(key);
        info.setValue(value);
        info.setParentId(parent.getId());
        info.setParentType(parent.getClass().toString());
        return info;
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }

    private Measurable generateSeries(final Measurable parent, final String label, final String axisLabel,
            final int low, final int high) {
        final Integer numObj = 10;
        final Random random = new Random();
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        final Date yesterday = cal.getTime();
        final TimeSeries timeSeries = new TimeSeries();

        timeSeries.setId(this.generateId());
        timeSeries.setLabel(label);
        timeSeries.setValueLabel(axisLabel);
        timeSeries.setParentId(parent.getId());
        timeSeries.setParentType(parent.getClass().toString());

        timeSeries.setParentType(ModelType.getForModel(parent.getClass()).getType());

        for (int j = 0; j < numObj; j++) {
            final SeriesElement elem = new SeriesElement();
            elem.setId(this.generateId());
            elem.setValue((random.nextInt() % (high - low)) + low);
            elem.setTimestamp(yesterday.getTime() + (j * 60000));
            elem.setParentId(timeSeries.getId());
            elem.setParentType(timeSeries.getClass().toString());
            timeSeries.getSeries().add(elem);
        }
        parent.addTimeSeries(timeSeries);
        return parent;
    }

    /*------------------------------------------------------
     *                 unused functions
     * -----------------------------------------------------
    public List<StatusInfo> generateInfos(Measurable parent){
        final Integer numObj = 10;
        final Long timestamp = new Date().getTime();
        final List<StatusInfo> infos = new LinkedList<>();
    
        for (int i = 0; i < numObj ; i++) {
            final StatusInfo info = new StatusInfo(timestamp, "Key", "Value");
            info.setId(generateId());
            infos.add(info);
            addParent(info, parent);
        }
        return infos;
    }
    
    public void setRevision(RevisionedBean bean){
        bean.setRevisionNumber(0L);
        bean.setChangelogSequence(0L);
        bean.setLastUpdate(new Date());
    }
    
    private StatusInfo createCommunicationStatusInfo(Measurable parent){
        final StatusInfo info = new StatusInfo();
        info.setTimestamp(new Date().getTime());
        info.setKey("connections");
        info.setValue(random.nextInt(500) + "");
    
        addParent(info, parent);
        return info;
    }
    
    private StatusInfo createRandomInfo(){
        return createInfo("info", random.nextInt(200)+"");
    }
    
    
    
    
    private TimeSeries createRandomSeries() {
        return createRandomSeries(20);
    }
    
    private void addParent(NestedMeasurement base, Measurable parent) {
        base.setParentId(parent.getId());
        base.setParentType(ModelType.getForModel(parent.getClass()).getType());
    }
    
    private TimeSeries createRandomSeries(int size){
        final TimeSeries series = new TimeSeries();
        series.setId(generateId());
        final List<SeriesElement> list = new LinkedList<>();
    
        for (int i = 0; i < size; i++) {
            final SeriesElement element = new SeriesElement();
            element.setValue(random.nextInt(100));
            element.setId(generateId());
            list.add(element);
        }
        series.setSeries(list);
        return series;
    }*/
}
