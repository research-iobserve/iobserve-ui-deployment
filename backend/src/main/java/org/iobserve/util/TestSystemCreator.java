package org.iobserve.util;

import org.iobserve.models.*;
import org.iobserve.models.System;
import org.iobserve.models.util.*;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;
import java.util.*;

/**
 * @author Christoph Dornieden <cdor@informatik.uni-kiel.de>
 */
public class TestSystemCreator {
    private static final String REST = "REST";
    private static final String TCP = "TCP/IP";
    
	private final Random random;
    private final EntityManagerFactory entityManagerFactory;

    @Inject
    public TestSystemCreator(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.random = new Random();
    }

    @Transactional
    public void createTestSystem(String systemId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        System oldSystem = entityManager.find(System.class, systemId);
        if(oldSystem != null) {
            EntityTransaction tx = entityManager.getTransaction();
            tx.begin();
            entityManager.remove(oldSystem);
            tx.commit();
        }
        System system = new System();
        system.setId(systemId);
        system.setName("Test System");

        // nodes
        Node webFrontendNode = createNode(systemId, "node", 1, "Web Frontend", "web-frontend", "172.17.0.2", 20, 40);
        Node storeServiceNode = createNode(systemId, "node", 2, "Store Service", "store1", "172.17.0.4", 40, 70);
        Node enterpriseServiceNode = createNode(systemId, "node", 3, "Enterprise Service", "enterprise", "172.17.0.5", 2, 3);
        Node registryServiceNode = createNode(systemId, "node", 4, "Registry Service", "register", "172.17.0.3", 0, 1);
        Node serviceAdapterNode = createNode(systemId, "node", 5, "Service Adapter", "adapter", "172.17.0.6", 2, 7);
        Node databaseNode = createNode(systemId, "node", 6, "Data Center", "psql1", "172.17.0.8", 1, 2);

        // node group - has both nodes
        NodeGroup nodeGroup1 = prepareDummy(new NodeGroup(), systemId, "nodeGroup", 1);
        nodeGroup1.setName("CoCoME");
        nodeGroup1.setNodes(Arrays.asList(webFrontendNode, storeServiceNode, enterpriseServiceNode, registryServiceNode, serviceAdapterNode, databaseNode));

        webFrontendNode.setNodeGroup(nodeGroup1);
        storeServiceNode.setNodeGroup(nodeGroup1);
        enterpriseServiceNode.setNodeGroup(nodeGroup1);
        registryServiceNode.setNodeGroup(nodeGroup1);
        serviceAdapterNode.setNodeGroup(nodeGroup1);
        databaseNode.setNodeGroup(nodeGroup1);
        
        // service instances
        /** webfrontend */
        ServiceInstance cloudWebInstance = createServiceInstance(webFrontendNode, "org.cocome.cloud .web", 20, 40, systemId, 1);      
        /** store service */
        ServiceInstance storeCashdeskInstance = createServiceInstance(storeServiceNode, "org.cocome.cloud .logic.webservice .cashdeskline.cashdesk", 5, 8, systemId, 2);
        ServiceInstance storeTradingsystemCashdesklineInstance = createServiceInstance(storeServiceNode, "org.cocome .tradingsystem .cashdeskline", 7, 9, systemId, 3);
        ServiceInstance storeTradingsystemInventoryInstance = createServiceInstance(storeServiceNode, "org.cocome .tradingsystem .inventory", 92, 95, systemId, 4);
        storeTradingsystemInventoryInstance.setStatus(Status.WARNING);
        /** registry service */
        ServiceInstance registryServiceInstance = createServiceInstance(registryServiceNode, "org.cocome.cloud .registry.service", 0, 1, systemId, 5);
        /** enterprise service */
        ServiceInstance enterpriseInventoryInstance = createServiceInstance(enterpriseServiceNode, "org.cocome .tradingsystem .inventory", 1, 2, systemId, 6);
        ServiceInstance enterpriseServiceInstance = createServiceInstance(enterpriseServiceNode, "org.cocome.cloud .webservice .enterpriseservice", 0, 1, systemId, 7);
        /** service adapter */
        ServiceInstance serviceAdapterInstance = createServiceInstance(serviceAdapterNode, "org.cocome.cloud .sa.serviceprovider", 2, 7, systemId, 8);
        /** database */
        ServiceInstance databaseInstance = createServiceInstance(databaseNode, "Postgres", 1, 2, systemId, 9);
        
        // create services
        /** web frontend */
        Service cloudWebservice = createService("org.cocome.cloud.web", "", systemId, 1);
 
        /** store service */
        Service storeCashdeskService = createService("org.cocome.cloud .logic.webservice .cashdeskline.cashdesk", "", systemId, 2);
        Service storeTradingsystemCashdesklineService = createService("org.cocome .tradingsystem .cashdeskline", "", systemId, 3);
        Service storeTradingsystemInventoryService = createService("org.cocome .tradingsystem .inventory", "", systemId, 4);
       
        /** registry service */
        Service registryService = createService("org.cocome.cloud .registry.service", "", systemId, 5);

        /** enterprise service */
        Service enterpriseInventoryService = createService("org.cocome .tradingsystem .inventory", "", systemId, 6);
        Service enterpriseService = createService("org.cocome.cloud .webservice .enterpriseservice", "", systemId, 7);

        /** service adapter */
        Service serviceAdapter = createService("org.cocome.cloud .sa.serviceprovider", "", systemId, 8);

        /** database */
        Service databaseService = createService("Postgres", "", systemId, 9);

        // link service to instances and backwards
        link(cloudWebInstance, cloudWebservice);

        link(storeCashdeskInstance, storeCashdeskService);
        link(storeTradingsystemCashdesklineInstance, storeTradingsystemCashdesklineService);
        link(storeTradingsystemInventoryInstance, storeTradingsystemInventoryService);

        link(registryServiceInstance, registryService);

        link(enterpriseInventoryInstance, enterpriseInventoryService);
        link(enterpriseServiceInstance, enterpriseService);

        link(serviceAdapterInstance, serviceAdapter);

        link(databaseInstance, databaseService);

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
        CommunicationInstance comInstance1 = createCommunication(cloudWebInstance, storeCashdeskInstance, systemId, 1);
        
        CommunicationInstance comInstance2 = createCommunication(storeCashdeskInstance, storeTradingsystemCashdesklineInstance, systemId, 2);
        CommunicationInstance comInstance3 = createCommunication(storeTradingsystemCashdesklineInstance, storeTradingsystemInventoryInstance, systemId, 3);
        CommunicationInstance comInstance4 = createCommunication(storeTradingsystemInventoryInstance, serviceAdapterInstance, systemId, 4);
        CommunicationInstance comInstance5 = createCommunication(storeTradingsystemInventoryInstance, enterpriseInventoryInstance, systemId, 5);

        CommunicationInstance comInstance6 = createCommunication(serviceAdapterInstance, databaseInstance, systemId, 6);
        
        CommunicationInstance comInstance7 = createCommunication(enterpriseInventoryInstance, enterpriseServiceInstance, systemId, 7);
        CommunicationInstance comInstance8 = createCommunication(enterpriseServiceInstance, serviceAdapterInstance, systemId, 8);
        
        CommunicationInstance comInstance9 = createCommunication(storeTradingsystemInventoryInstance, registryServiceInstance, systemId, 9);
        CommunicationInstance comInstance10 = createCommunication(enterpriseServiceInstance, registryServiceInstance, systemId, 10);


        // communications - duplex
        Communication communication1 = createCommunication(cloudWebservice, storeCashdeskService, REST, comInstance1, systemId, 1);
    
        Communication communication2 = createCommunication(storeCashdeskService, storeTradingsystemCashdesklineService, TCP, comInstance2, systemId, 2);
        Communication communication3 = createCommunication(storeTradingsystemCashdesklineService, storeTradingsystemInventoryService, TCP, comInstance3, systemId, 3);
        Communication communication4 = createCommunication(storeTradingsystemInventoryService, serviceAdapter, TCP, comInstance4, systemId, 4);
        Communication communication5 = createCommunication(storeTradingsystemInventoryService, serviceAdapter, TCP, comInstance5, systemId, 5);
        
        Communication communication6 = createCommunication(serviceAdapter, databaseService, TCP, comInstance6, systemId, 6);
        
        Communication communication7 = createCommunication(enterpriseInventoryService, enterpriseService, TCP, comInstance7, systemId, 7);
        Communication communication8 = createCommunication(enterpriseService, serviceAdapter, TCP, comInstance8, systemId, 8);
        
        Communication communication9 = createCommunication(storeTradingsystemInventoryService, registryService, TCP, comInstance9, systemId, 9);
        Communication communication10 = createCommunication(enterpriseService, registryService, TCP, comInstance10, systemId, 10);

        system.setCommunications(Arrays.asList(communication1, communication2, communication3,communication4,communication5, communication6, communication7, communication8, communication9, communication10));
        system.setServices(Arrays.asList(cloudWebservice, 
        		storeCashdeskService, 
        		storeTradingsystemCashdesklineService, 
        		storeTradingsystemInventoryService, 
        		enterpriseInventoryService, 
        		enterpriseService,
        		registryService,
        		serviceAdapter,
        		databaseService));
        system.setNodeGroups(Collections.singletonList(nodeGroup1));

        EntityTransaction tx = entityManager.getTransaction();
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
    private Communication createCommunication(Service source, Service target, String technology, CommunicationInstance communicationInstance, String systemId, int counter) {
	    Communication communication = prepareDummy(new Communication(), systemId, "communication", counter);
	    communication.setSource(source);
	    communication.setTarget(target);
	    communication.setTechnology(technology);
	    
        communication.setInstances(Collections.singletonList(communicationInstance));
        
        communicationInstance.setCommunication(communication);
        communicationInstance.addStatusInformation(createInfo(communication,"connections", randomNumber(5,30).toString()));
        communicationInstance.setWorkload(10L);
	    
	    return communication;
    }

    
    private Integer randomNumber(int i, int j) {
		return random.nextInt()%(j-i)+i;
	}

	/**
     * 
     * @param source
     * @param target
     * @param systemId
     * @param counter
     */
    private CommunicationInstance createCommunication(ServiceInstance source, ServiceInstance target, String systemId, int counter) {
    	CommunicationInstance communicationInstance = prepareDummy(new CommunicationInstance(), systemId, "communicationInstance", counter);
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
    private void link(ServiceInstance instance, Service service) {
    	instance.setService(service);
        service.setInstances(Collections.singletonList(instance));
	}

	private Service createService(String name, String description, String systemId, int counter) {
	    Service service = prepareDummy(new Service(), systemId, "service", counter);
	    service.setName(name);
	    service.setDescription(description);
        generateSeries(service, "Requests","Number of Requests", 5, 8);
        
        return service;
    }
    
    /**
     * Create an instance of a service.
     * 
     * @param node the node where the service is deployed
     * @param instanceName name of the instance
     * @param systemId system id
     * @param counter counter
     * 
     * @return the service instance
     */
    private ServiceInstance createServiceInstance(Node node, String instanceName, int low, int high, String systemId, int counter) {
    	ServiceInstance serviceInstance = prepareDummy(new ServiceInstance(instanceName), systemId, "serviceInstance", counter);
    	serviceInstance.setNode(node);
        generateSeries(serviceInstance, "Requests","Number of Requests", low*15, high*15);
		return serviceInstance;
	}

	/**
     * Create one service node.
     * 
     * @param systemId the id of the software system this node belongs to
     * @param prefix 
     * @param counter 
     * @param name name of the node to be displayed
     * @param hostname hostname of then node
     * @param ip ip address of the node
     * 
     * @return returns a node for the visualization
     */
    private Node createNode(String systemId, String prefix, int counter, String name, String hostname, String ip, int low, int high) {
    	Node node = prepareDummy(new Node(), systemId, prefix, counter);
        node.setName(name);
        node.setHostname(hostname);
        node.setIp(ip);
        generateSeries(node, "Utilization", "%", low, high);
        
        return node;
    }

    /**
     * Configure a given object.
     * 
     * @param bean the object
     * @param systemId the system id
     * @param prefix the common prefix for this type of object
     * @param counter the counter id.
     * 
     * @return returns the configured object
     */
    private <Bean extends RevisionedBean> Bean prepareDummy(Bean bean, String systemId, String prefix, int counter) {
        bean.setId(generateTestId(systemId, prefix, counter));
        bean.setSystemId(systemId);
        bean.setChangelogSequence(0L);
        bean.setLastUpdate(new Date());
        bean.setRevisionNumber(0L);
        return bean;
    }

    private String generateTestId(String systemId, String prefix, int counter){
        return "test-"+systemId+"-"+prefix+"-"+counter;
    }

    private StatusInfo createInfo(Measurable parent, String key, String value){
        final StatusInfo info = new StatusInfo();
        info.setId(generateId());
        info.setTimestamp(new Date().getTime());
        info.setKey(key);
        info.setValue(value);
        info.setParentId(parent.getId());
        info.setParentType(parent.getClass().toString());
        return info;
    }

    private String generateId(){
        return UUID.randomUUID().toString();
    }

    private Measurable generateSeries(Measurable parent, String label, String axisLabel, int low, int high) {
        final Integer numObj = 10;
        final Random random = new Random();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        final Date yesterday = cal.getTime();
        final TimeSeries timeSeries = new TimeSeries();

        timeSeries.setId(generateId());
        timeSeries.setLabel(label);
        timeSeries.setValueLabel(axisLabel);
        timeSeries.setParentId(parent.getId());
        timeSeries.setParentType(parent.getClass().toString());

        timeSeries.setParentType(ModelType.getForModel(parent.getClass()).getType());

        for (int j = 0; j <numObj ; j++) {
            final SeriesElement elem = new SeriesElement();
            elem.setId(generateId());
            elem.setValue(random.nextInt()%(high-low)+low);
            elem.setTimestamp(yesterday.getTime()+(j*60000));
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
