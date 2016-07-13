package org.iobserve.resources;

import org.iobserve.models.*;
import org.iobserve.models.System;
import org.iobserve.models.util.RevisionedBean;
import org.iobserve.models.util.SeriesElement;
import org.iobserve.models.util.StatusInfo;
import org.iobserve.models.util.TimeSeries;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.*;

/**
 * Created by cdor on 03.06.16.
 */
@Path("v1")
public class CreateDummySystemResource {
    Random random = new Random();

    @Inject
    private EntityManagerFactory entityManagerFactory;


    @POST
    @Path("systems/createTest/{id}")
    public void createTestWithId(@PathParam("id") String id) {
        try {
            createTestSystem(id);
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Transactional
    private void createTestSystem(String systemId) {
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

        Node node2 = prepareDummy(new Node(), systemId, "node", 2);
        node2.setName("Logicnode");
        node2.setHostname("host2");
        node2.setIp("10.0.0.2");

        Node node3 = prepareDummy(new Node(), systemId, "node", 3);
        node3.setName("Adapter");
        node3.setHostname("host3");
        node3.setIp("10.0.0.2");

        Node node4 = prepareDummy(new Node(), systemId, "node", 4);
        node4.setName("Database");
        node4.setHostname("host4");
        node4.setIp("10.0.0.2");


        // node group - has both nodes
        NodeGroup nodeGroup1 = prepareDummy(new NodeGroup(), systemId, "nodeGroup", 1);
        nodeGroup1.setName("CoCoME");
        nodeGroup1.setNodes(Arrays.asList(node1, node2, node3, node4));
        node1.setNodeGroup(nodeGroup1);
        node2.setNodeGroup(nodeGroup1);
        node3.setNodeGroup(nodeGroup1);
        node4.setNodeGroup(nodeGroup1);


        // service instances
        ServiceInstance serviceInstance1 = prepareDummy(new ServiceInstance("FrontEnd"), systemId, "serviceInstance", 1);
        serviceInstance1.setNode(node1);

        ServiceInstance serviceInstance2 = prepareDummy(new ServiceInstance("WebService"), systemId, "serviceInstance", 2);
        serviceInstance2.setNode(node2);

        ServiceInstance serviceInstance3 = prepareDummy(new ServiceInstance("CashDesk"), systemId, "serviceInstance", 3);
        serviceInstance3.setNode(node2);

        ServiceInstance serviceInstance4 = prepareDummy(new ServiceInstance("Inventory"), systemId, "serviceInstance", 4);
        serviceInstance4.setNode(node2);

        ServiceInstance serviceInstance5 = prepareDummy(new ServiceInstance("Data"), systemId, "serviceInstance", 5);
        serviceInstance5.setNode(node3);

        ServiceInstance serviceInstance6 = prepareDummy(new ServiceInstance("PostgreSQL"), systemId, "serviceInstance", 6);
        serviceInstance6.setNode(node4);

        // create services
        Service service1 = prepareDummy(new Service(), systemId, "service", 1);
        service1.setName("Front End");
        service1.setDescription("A dummy description!");

        Service service2 = prepareDummy(new Service(), systemId, "service", 2);
        service2.setName("WebService");
        service2.setDescription("Another dummy description");

        Service service3 = prepareDummy(new Service(), systemId, "service", 3);
        service3.setName("CashDesk");
        service3.setDescription("Another dummy description");

        Service service4 = prepareDummy(new Service(), systemId, "service", 4);
        service4.setName("Inventory");
        service4.setDescription("Another dummy description");

        Service service5 = prepareDummy(new Service(), systemId, "service", 5);
        service5.setName("Data");
        service5.setDescription("Another dummy description");

        Service service6 = prepareDummy(new Service(), systemId, "service", 6);
        service6.setName("PostgreSQL");
        service6.setDescription("Another dummy description");



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

        CommunicationInstance communicationInstance2 = prepareDummy(new CommunicationInstance(), systemId, "communicationInstance", 2);
        communicationInstance2.setSource(serviceInstance2);
        communicationInstance2.setTarget(serviceInstance3);


        CommunicationInstance communicationInstance3 = prepareDummy(new CommunicationInstance(), systemId, "communicationInstance", 3);
        communicationInstance3.setSource(serviceInstance3);
        communicationInstance3.setTarget(serviceInstance4);

        CommunicationInstance communicationInstance4 = prepareDummy(new CommunicationInstance(), systemId, "communicationInstance", 4);
        communicationInstance4.setSource(serviceInstance3);
        communicationInstance4.setTarget(serviceInstance5);

        CommunicationInstance communicationInstance5 = prepareDummy(new CommunicationInstance(), systemId, "communicationInstance", 5);
        communicationInstance5.setSource(serviceInstance4);
        communicationInstance5.setTarget(serviceInstance5);

        CommunicationInstance communicationInstance6 = prepareDummy(new CommunicationInstance(), systemId, "communicationInstance", 6);
        communicationInstance6.setSource(serviceInstance5);
        communicationInstance6.setTarget(serviceInstance6);

        // communications - duplex
        Communication communication1 = prepareDummy(new Communication(), systemId, "communication", 1);
        communication1.setSource(service1);
        communication1.setTarget(service2);
        communication1.setTechnology("REST");

        Communication communication2 = prepareDummy(new Communication(), systemId, "communication", 2);
        communication2.setSource(service2);
        communication2.setTarget(service3);
        communication2.setTechnology("TCP/IP");

        Communication communication3 = prepareDummy(new Communication(), systemId, "communication", 3);
        communication3.setSource(service3);
        communication3.setTarget(service4);
        communication3.setTechnology("TCP/IP");


        Communication communication4 = prepareDummy(new Communication(), systemId, "communication", 4);
        communication4.setSource(service3);
        communication4.setTarget(service5);
        communication4.setTechnology("TCP/IP");


        Communication communication5 = prepareDummy(new Communication(), systemId, "communication", 5);
        communication5.setSource(service4);
        communication5.setTarget(service5);
        communication5.setTechnology("TCP/IP");

        Communication communication6 = prepareDummy(new Communication(), systemId, "communication", 6);
        communication6.setSource(service5);
        communication6.setTarget(service6);
        communication6.setTechnology("TCP/IP");



        communication1.setInstances(Collections.singletonList(communicationInstance1));
        communication2.setInstances(Collections.singletonList(communicationInstance2));
        communication3.setInstances(Collections.singletonList(communicationInstance3));
        communication4.setInstances(Collections.singletonList(communicationInstance4));
        communication5.setInstances(Collections.singletonList(communicationInstance5));
        communication6.setInstances(Collections.singletonList(communicationInstance6));

        communicationInstance1.setCommunication(communication1);
        communicationInstance1.addStatusInformation(createInfo("connectrions", "10"));
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



    public String generateId(){
        return UUID.randomUUID().toString();
    }

    public List<TimeSeries> generateSeries(){
        final Integer numObj = 10;
        final Long timestamp = new Date().getTime();
        final List<TimeSeries> series = new LinkedList<>();

        for (int i = 0; i < numObj; i++) {
            final List<SeriesElement> seriesElements = new LinkedList<>();
            for (int j = 0; j <numObj ; j++) {
                final SeriesElement elem = new SeriesElement();
                elem.setId(generateId());
                elem.setValue(i);
            }
            final TimeSeries timeSeries = new TimeSeries("test",timestamp,timestamp+100,seriesElements);
            timeSeries.setId(generateId());
            series.add(timeSeries);
        }
        return series;
    }

    public List<StatusInfo> generateInfos(){
        final Integer numObj = 10;
        final Long timestamp = new Date().getTime();
        final List<StatusInfo> infos = new LinkedList<>();

        for (int i = 0; i < numObj ; i++) {
            final StatusInfo info = new StatusInfo(timestamp, "Key", "Value");
            info.setId(generateId());
            infos.add(info);
        }
        return infos;
    }

    public void setRevision(RevisionedBean bean){
        bean.setRevisionNumber(0L);
        bean.setChangelogSequence(0L);
        bean.setLastUpdate(new Date());
    }

    private StatusInfo createCommunicationStatusInfo(){
        final StatusInfo info = new StatusInfo();
        info.setTimestamp(new Date().getTime());
        info.setKey("connections");
        info.setValue(random.nextInt(500) + "");
        return info;
    }

    private StatusInfo createRandomInfo(){
        return createInfo("info", random.nextInt(200)+"");
    }


    private StatusInfo createInfo(String key, String value){
        final StatusInfo info = new StatusInfo();
        info.setId(generateId());
        info.setTimestamp(new Date().getTime());
        info.setKey(key);
        info.setValue(value);
        return info;
    }

    private TimeSeries createRandomSeries() {
        return createRandomSeries(20);
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
    }
}
