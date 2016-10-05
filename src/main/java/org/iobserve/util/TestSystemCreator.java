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
        Node node1 = prepareDummy(new Node(), systemId, "node", 1);
        node1.setName("WebNode");
        node1.setHostname("test hostname");
        node1.setIp("10.0.0.1");
        generateSeries(node1, "Utilization", "%");
        generateSeries(node1, "Power Consumption", "kWh");


        Node node2 = prepareDummy(new Node(), systemId, "node", 2);
        node2.setName("LogicNode");
        node2.setHostname("host2");
        node2.setIp("10.0.0.2");
        generateSeries(node2, "Utilization", "%");
        generateSeries(node2, "Power Consumption", "kWh");

        Node node3 = prepareDummy(new Node(), systemId, "node", 3);
        node3.setName("Adapter");
        node3.setHostname("host3");
        node3.setIp("10.0.0.2");
        generateSeries(node3, "Utilization", "%");
        generateSeries(node3, "Power Consumption", "kWh");

        Node node4 = prepareDummy(new Node(), systemId, "node", 4);
        node4.setName("DataCenter");
        node4.setHostname("host4");
        node4.setIp("10.0.0.2");
        generateSeries(node4, "Utilization", "%");
        generateSeries(node4, "Power Consumption", "kWh");


        // node group - has both nodes
        NodeGroup nodeGroup1 = prepareDummy(new NodeGroup(), systemId, "nodeGroup", 1);
        nodeGroup1.setName("CoCoME");
        nodeGroup1.setNodes(Arrays.asList(node1, node2, node3, node4));
        generateSeries(node3, "Sub-Nodes","Number of Sub-Nodes");
        generateSeries(node3, "Errors","Number of Errors");

        node1.setNodeGroup(nodeGroup1);
        node2.setNodeGroup(nodeGroup1);
        node3.setNodeGroup(nodeGroup1);
        node4.setNodeGroup(nodeGroup1);


        // service instances
        ServiceInstance serviceInstance1 = prepareDummy(new ServiceInstance("Frontend"), systemId, "serviceInstance", 1);
        serviceInstance1.setNode(node1);
        generateSeries(serviceInstance1, "Failures","Number of Failures");
        generateSeries(serviceInstance1, "Queries","Number of Queries");

        ServiceInstance serviceInstance2 = prepareDummy(new ServiceInstance("WebService"), systemId, "serviceInstance", 2);
        serviceInstance2.setNode(node2);
        generateSeries(serviceInstance2, "Failures","Number of Failures");
        generateSeries(serviceInstance2, "Queries","Number of Queries");

        ServiceInstance serviceInstance3 = prepareDummy(new ServiceInstance("CashDesk"), systemId, "serviceInstance", 3);
        serviceInstance3.setNode(node2);
        generateSeries(serviceInstance3, "Failures","Number of Failures");
        generateSeries(serviceInstance3, "Queries","Number of Queries");

        ServiceInstance serviceInstance4 = prepareDummy(new ServiceInstance("Inventory"), systemId, "serviceInstance", 4);
        serviceInstance4.setNode(node2);
        serviceInstance4.setStatus(Status.WARNING);
        generateSeries(serviceInstance4, "Failures","Number of Failures");
        generateSeries(serviceInstance4, "Queries","Number of Queries");

        ServiceInstance serviceInstance5 = prepareDummy(new ServiceInstance("Data"), systemId, "serviceInstance", 5);
        serviceInstance5.setNode(node3);
        generateSeries(serviceInstance5, "Failures","Number of Failures");
        generateSeries(serviceInstance5, "Queries","Number of Queries");

        ServiceInstance serviceInstance6 = prepareDummy(new ServiceInstance("Database"), systemId, "serviceInstance", 6);
        serviceInstance6.setNode(node4);
        generateSeries(serviceInstance6, "Failures","Number of Failures");
        generateSeries(serviceInstance6, "Queries","Number of Queries");

        // create services
        Service service1 = prepareDummy(new Service(), systemId, "service", 1);
        service1.setName("Front End");
        service1.setDescription("A dummy description!");
        generateSeries(service1, "Failures","Number of Failures");
        generateSeries(service1, "Queries","Number of Queries");

        Service service2 = prepareDummy(new Service(), systemId, "service", 2);
        service2.setName("WebService");
        service2.setDescription("Another dummy description");
        generateSeries(service2, "Failures","Number of Failures");
        generateSeries(service2, "Queries","Number of Queries");

        Service service3 = prepareDummy(new Service(), systemId, "service", 3);
        service3.setName("CashDesk");
        service3.setDescription("Another dummy description");
        generateSeries(service3, "Failures","Number of Failures");
        generateSeries(service3, "Queries","Number of Queries");

        Service service4 = prepareDummy(new Service(), systemId, "service", 4);
        service4.setName("Inventory");
        service4.setDescription("Another dummy description");
        generateSeries(service4, "Failures","Number of Failures");
        generateSeries(service4, "Queries","Number of Queries");

        Service service5 = prepareDummy(new Service(), systemId, "service", 5);
        service5.setName("Data");
        service5.setDescription("Another dummy description");
        generateSeries(service5, "Failures","Number of Failures");
        generateSeries(service5, "Queries","Number of Queries");

        Service service6 = prepareDummy(new Service(), systemId, "service", 6);
        service6.setName("PostgreSQL");
        service6.setDescription("Another dummy description");
        generateSeries(service6, "Failures","Number of Failures");
        generateSeries(service6, "Queries","Number of Queries");



        // link service to instances and backwards
        serviceInstance1.setService(service1);
        service1.setInstances(Collections.singletonList(serviceInstance1));

        serviceInstance2.setService(service2);
        service2.setInstances(Collections.singletonList(serviceInstance2));

        serviceInstance3.setService(service3);
        service3.setInstances(Collections.singletonList(serviceInstance3));

        serviceInstance4.setService(service4);
        service4.setInstances(Collections.singletonList(serviceInstance4));

        serviceInstance5.setService(service5);
        service5.setInstances(Collections.singletonList(serviceInstance5));

        serviceInstance6.setService(service6);
        service6.setInstances(Collections.singletonList(serviceInstance6));

        // write services back to node
        node1.setServices(Collections.singletonList(serviceInstance1));
        node2.setServices(Collections.singletonList(serviceInstance2));
        node2.setServices(Collections.singletonList(serviceInstance3));
        node2.setServices(Collections.singletonList(serviceInstance4));
        node3.setServices(Collections.singletonList(serviceInstance5));
        node4.setServices(Collections.singletonList(serviceInstance6));

        // communication instances - duplex communication
        CommunicationInstance communicationInstance1 = prepareDummy(new CommunicationInstance(), systemId, "communicationInstance", 1);
        communicationInstance1.setSource(serviceInstance1);
        communicationInstance1.setTarget(serviceInstance2);
        generateSeries(communicationInstance1, "Connections","Number of Connections");
        generateSeries(communicationInstance1, "Lost Packages","Number of Lost Packages");

        CommunicationInstance communicationInstance2 = prepareDummy(new CommunicationInstance(), systemId, "communicationInstance", 2);
        communicationInstance2.setSource(serviceInstance2);
        communicationInstance2.setTarget(serviceInstance3);
        generateSeries(communicationInstance2, "Connections","Number of Connections");
        generateSeries(communicationInstance2, "Lost Packages","Number of Lost Packages");


        CommunicationInstance communicationInstance3 = prepareDummy(new CommunicationInstance(), systemId, "communicationInstance", 3);
        communicationInstance3.setSource(serviceInstance3);
        communicationInstance3.setTarget(serviceInstance4);
        generateSeries(communicationInstance3, "Connections","Number of Connections");
        generateSeries(communicationInstance3, "Lost Packages","Number of Lost Packages");

        CommunicationInstance communicationInstance4 = prepareDummy(new CommunicationInstance(), systemId, "communicationInstance", 4);
        communicationInstance4.setSource(serviceInstance3);
        communicationInstance4.setTarget(serviceInstance5);
        generateSeries(communicationInstance4, "Connections","Number of Connections");
        generateSeries(communicationInstance4, "Lost Packages","Number of Lost Packages");

        CommunicationInstance communicationInstance5 = prepareDummy(new CommunicationInstance(), systemId, "communicationInstance", 5);
        communicationInstance5.setSource(serviceInstance4);
        communicationInstance5.setTarget(serviceInstance5);
        generateSeries(communicationInstance5, "Connections","Number of Connections");
        generateSeries(communicationInstance5, "Lost Packages","Number of Lost Packages");

        CommunicationInstance communicationInstance6 = prepareDummy(new CommunicationInstance(), systemId, "communicationInstance", 6);
        communicationInstance6.setSource(serviceInstance5);
        communicationInstance6.setTarget(serviceInstance6);
        generateSeries(communicationInstance6, "Connections","Number of Connections");
        generateSeries(communicationInstance6, "Lost Packages","Number of Lost Packages");

        // communications - duplex
        Communication communication1 = prepareDummy(new Communication(), systemId, "communication", 1);
        communication1.setSource(service1);
        communication1.setTarget(service2);
        communication1.setTechnology("REST");
        generateSeries(communication1, "Connections","Number of Connections");
        generateSeries(communication1, "Lost Packages","Number of Lost Packages");

        Communication communication2 = prepareDummy(new Communication(), systemId, "communication", 2);
        communication2.setSource(service2);
        communication2.setTarget(service3);
        communication2.setTechnology("TCP/IP");
        generateSeries(communication2, "Connections","Number of Connections");
        generateSeries(communication2, "Lost Packages","Number of Lost Packages");

        Communication communication3 = prepareDummy(new Communication(), systemId, "communication", 3);
        communication3.setSource(service3);
        communication3.setTarget(service4);
        communication3.setTechnology("TCP/IP");
        generateSeries(communication3, "Connections","Number of Connections");
        generateSeries(communication3, "Lost Packages","Number of Lost Packages");


        Communication communication4 = prepareDummy(new Communication(), systemId, "communication", 4);
        communication4.setSource(service3);
        communication4.setTarget(service5);
        communication4.setTechnology("TCP/IP");
        generateSeries(communication4, "Connections","Number of Connections");
        generateSeries(communication4, "Lost Packages","Number of Lost Packages");


        Communication communication5 = prepareDummy(new Communication(), systemId, "communication", 5);
        communication5.setSource(service4);
        communication5.setTarget(service5);
        communication5.setTechnology("TCP/IP");
        generateSeries(communication5, "Connections","Number of Connections");
        generateSeries(communication5, "Lost Packages","Number of Lost Packages");

        Communication communication6 = prepareDummy(new Communication(), systemId, "communication", 6);
        communication6.setSource(service5);
        communication6.setTarget(service6);
        communication6.setTechnology("TCP/IP");
        generateSeries(communication6, "Connections","Number of Connections");
        generateSeries(communication6, "Lost Packages","Number of Lost Packages");



        communication1.setInstances(Collections.singletonList(communicationInstance1));
        communication2.setInstances(Collections.singletonList(communicationInstance2));
        communication3.setInstances(Collections.singletonList(communicationInstance3));
        communication4.setInstances(Collections.singletonList(communicationInstance4));
        communication5.setInstances(Collections.singletonList(communicationInstance5));
        communication6.setInstances(Collections.singletonList(communicationInstance6));

        communicationInstance1.setCommunication(communication1);
        communicationInstance1.addStatusInformation(createInfo("connections", "10"));
        communicationInstance1.setWorkload(10L);

        communicationInstance2.setCommunication(communication2);
        communicationInstance2.addStatusInformation(createInfo("connections", "20"));
        communicationInstance2.setWorkload(20L);

        communicationInstance3.setCommunication(communication3);
        communicationInstance3.addStatusInformation(createInfo("connections", "10"));
        communicationInstance3.setWorkload(10L);

        communicationInstance4.setCommunication(communication4);
        communicationInstance4.addStatusInformation(createInfo("connections", "15"));
        communicationInstance4.setWorkload(15L);

        communicationInstance5.setCommunication(communication5);
        communicationInstance5.addStatusInformation(createInfo("connections", "10"));
        communicationInstance5.setWorkload(10L);

        communicationInstance6.setCommunication(communication6);
        communicationInstance6.addStatusInformation(createInfo("connections", "15"));
        communicationInstance6.setWorkload(15L);


        system.setCommunications(Arrays.asList(communication1, communication2, communication3,communication4,communication5, communication6));
        system.setServices(Arrays.asList(service1, service2, service3, service4,service5,service6));
        system.setNodeGroups(Collections.singletonList(nodeGroup1));

        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(system); // saves all thanks to cascading
        tx.commit();
        entityManager.close();
    }

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

    private StatusInfo createInfo(String key, String value){
        final StatusInfo info = new StatusInfo();
        info.setId(generateId());
        info.setTimestamp(new Date().getTime());
        info.setKey(key);
        info.setValue(value);
        return info;
    }

    private String generateId(){
        return UUID.randomUUID().toString();
    }

    private Measurable generateSeries(Measurable parent, String label, String axisLabel){
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

        timeSeries.setParentType(ModelType.getForModel(parent.getClass()).getType());

        for (int j = 0; j <numObj ; j++) {
            final SeriesElement elem = new SeriesElement();
            elem.setId(generateId());
            elem.setValue(random.nextInt(numObj));
            elem.setTimestamp(yesterday.getTime()+(j*60000));
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
