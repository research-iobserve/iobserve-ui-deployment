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
    @Path("systems/createDummy")
    public String createDummy() {
        createBoostrapData(null);
        return "dummy created";
    }

    @POST
    @Path("systems/createDummy/{id}")
    public String createDummyWithId(@PathParam("id") String id) {
        String message = createBoostrapData(id);
        return message;
    }

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

    // might look a bit weird, was Java->Scala->Java in the past
    @Transactional
    private String createBoostrapData(String id){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        final Integer numObj = 100;

        //Nodes
        final List<Node> nodes0 = new LinkedList<Node>();
        final List<Node> nodes1 = new LinkedList<Node>();
        final List<Node> nodes2 = new LinkedList<Node>();


        for (int i = 0; i < numObj ; i++) {
            nodes0.add(i, new Node("name", new LinkedList<ServiceInstance>(), "127.0.0.1", "bernd"));
            nodes1.add(i, new Node("name", new LinkedList<ServiceInstance>(), "127.0.0.1", "bernd"));
            nodes2.add(i, new Node("name", new LinkedList<ServiceInstance>(), "127.0.0.1", "bernd"));

            nodes0.get(i).setId(generateId());
            nodes0.get(i).setTimeSeries(generateSeries());
            nodes0.get(i).setStatusInformations(generateInfos());
            setRevision(nodes0.get(i));

            nodes1.get(i).setId(generateId());
            nodes1.get(i).setTimeSeries(generateSeries());
            nodes1.get(i).setStatusInformations(generateInfos());
            setRevision(nodes1.get(i));



            nodes2.get(i).setId(generateId());
            nodes2.get(i).setTimeSeries(generateSeries());
            nodes2.get(i).setStatusInformations(generateInfos());
            setRevision(nodes2.get(i));


            nodes0.get(i).getServices().add(new ServiceInstance("Service "+ i + "" + 1));
            nodes0.get(i).getServices().get(0).setNode(nodes0.get(i));
            nodes1.get(i).getServices().add(new ServiceInstance("Service "+ i + "" + 1));
            nodes1.get(i).getServices().get(0).setNode(nodes1.get(i));
            nodes1.get(i).getServices().add(new ServiceInstance("Service "+ i + "" + 2));
            nodes1.get(i).getServices().get(1).setNode(nodes1.get(i));
            nodes2.get(i).getServices().add(new ServiceInstance("Service "+ i + "" + 1));
            nodes2.get(i).getServices().get(0).setNode(nodes2.get(i));
            nodes2.get(i).getServices().add(new ServiceInstance("Service "+ i + "" + 2));
            nodes2.get(i).getServices().get(1).setNode(nodes2.get(i));
            nodes2.get(i).getServices().add(new ServiceInstance("Service "+ i + "" + 3));
            nodes2.get(i).getServices().get(2).setNode(nodes2.get(i));
        }

        //Nodegroups
        final NodeGroup nodeGroup0 = new NodeGroup("Group0", nodes0);
        final NodeGroup nodeGroup1 = new NodeGroup("Group1", nodes1);
        final NodeGroup nodeGroup2 = new NodeGroup("Group2", nodes2);
        final List<NodeGroup> groups = new LinkedList<NodeGroup>();
        groups.add(nodeGroup0);
        groups.add(nodeGroup1);
        groups.add(nodeGroup2);

        for(NodeGroup group: groups){
            group.setId(generateId());
            group.setTimeSeries(generateSeries());
            group.setStatusInformations(generateInfos());
            setRevision(group);
        }


        //ServiceInstances
        final List<ServiceInstance> serviceInstances0 = new LinkedList<>();
        final List<ServiceInstance> serviceInstances1 = new LinkedList<>();
        final List<ServiceInstance> serviceInstances2 = new LinkedList<>();
        final List<ServiceInstance> serviceInstances3 = new LinkedList<>();
        final List<ServiceInstance> serviceInstances4 = new LinkedList<>();
        final List<ServiceInstance> serviceInstances5 = new LinkedList<>();

        for (int i = 0; i < numObj; i++) {
            serviceInstances0.add(nodes0.get(i).getServices().get(0));
            serviceInstances1.add(nodes1.get(i).getServices().get(0));
            serviceInstances2.add(nodes1.get(i).getServices().get(1));
            serviceInstances3.add(nodes2.get(i).getServices().get(0));
            serviceInstances4.add(nodes2.get(i).getServices().get(1));
            serviceInstances5.add(nodes2.get(i).getServices().get(2));
        }

        for (int i = 0; i < numObj; i++) {
            serviceInstances0.get(i).setId(generateId());
            serviceInstances0.get(i).setTimeSeries(generateSeries());
            serviceInstances0.get(i).setStatusInformations(generateInfos());
            setRevision(serviceInstances0.get(i));

            serviceInstances1.get(i).setId(generateId());
            serviceInstances1.get(i).setTimeSeries(generateSeries());
            serviceInstances1.get(i).setStatusInformations(generateInfos());
            setRevision(serviceInstances1.get(i));

            serviceInstances2.get(i).setId(generateId());
            serviceInstances2.get(i).setTimeSeries(generateSeries());
            serviceInstances2.get(i).setStatusInformations(generateInfos());
            setRevision(serviceInstances2.get(i));

            serviceInstances3.get(i).setId(generateId());
            serviceInstances3.get(i).setTimeSeries(generateSeries());
            serviceInstances3.get(i).setStatusInformations(generateInfos());
            setRevision(serviceInstances3.get(i));

            serviceInstances4.get(i).setId(generateId());
            serviceInstances4.get(i).setTimeSeries(generateSeries());
            serviceInstances4.get(i).setStatusInformations(generateInfos());
            setRevision(serviceInstances4.get(i));

            serviceInstances5.get(i).setId(generateId());
            serviceInstances5.get(i).setTimeSeries(generateSeries());
            serviceInstances5.get(i).setStatusInformations(generateInfos());
            setRevision(serviceInstances5.get(i));
        }




        //Services
        final Service service0 = new Service("Service0","Dummy",serviceInstances0);
        final Service service1 = new Service("Service1","Dummy",serviceInstances1);
        final Service service2 = new Service("Service2","Dummy",serviceInstances2);
        final Service service3 = new Service("Service3","Dummy",serviceInstances3);
        final Service service4 = new Service("Service4","Dummy",serviceInstances4);
        final Service service5 = new Service("Service5","Dummy",serviceInstances5);

        for (int i = 0; i < numObj; i++) {
            service0.getInstances().get(i).setService(service0);
            service1.getInstances().get(i).setService(service1);
            service2.getInstances().get(i).setService(service2);
            service3.getInstances().get(i).setService(service3);
            service4.getInstances().get(i).setService(service4);
            service5.getInstances().get(i).setService(service5);
        }

        final List<Service> services = new LinkedList<>();
        services.add(service0);
        services.add(service1);
        services.add(service2);
        services.add(service3);
        services.add(service4);
        services.add(service5);

        for(Service service:services){
            service.setId(generateId());
            service.setTimeSeries(generateSeries());
            service.setStatusInformations(generateInfos());
            setRevision(service);
        }


        //CommunicationInstancses
        final List<CommunicationInstance> communicationInstances0 = new LinkedList<>();
        final List<CommunicationInstance> communicationInstances1 = new LinkedList<>();
        final List<CommunicationInstance> communicationInstances2 = new LinkedList<>();

        final Communication communication0 =
                new Communication(service0,service1,communicationInstances0, "Tech0");
        final Communication communication1 =
                new Communication(service2,service3,communicationInstances1, "Tech1");
        final Communication communication2 =
                new Communication(service4,service5,communicationInstances2, "Tech2");

        for (int i = 0; i < numObj ; i++) {
            communicationInstances0.add(new CommunicationInstance(
                    service0.getInstances().get(i),
                    service1.getInstances().get(i)));
            communicationInstances0.get(i).setCommunication(communication0);

            communicationInstances1.add(new CommunicationInstance(
                    service2.getInstances().get(i),
                    service3.getInstances().get(i)));
            communicationInstances0.get(i).setCommunication(communication1);

            communicationInstances2.add(new CommunicationInstance(
                    service4.getInstances().get(i),
                    service5.getInstances().get(i)));
            communicationInstances0.get(i).setCommunication(communication2);
        }

        for (int i = 0; i < numObj ; i++) {
            communicationInstances0.get(i).setId(generateId());
            communicationInstances0.get(i).setTimeSeries(generateSeries());
            communicationInstances0.get(i).setStatusInformations(generateInfos());
            setRevision(communicationInstances0.get(i));

            communicationInstances1.get(i).setId(generateId());
            communicationInstances1.get(i).setTimeSeries(generateSeries());
            communicationInstances1.get(i).setStatusInformations(generateInfos());
            setRevision(communicationInstances1.get(i));

            communicationInstances2.get(i).setId(generateId());
            communicationInstances2.get(i).setTimeSeries(generateSeries());
            communicationInstances2.get(i).setStatusInformations(generateInfos());
            setRevision(communicationInstances2.get(i));
        }

        final List<Communication> communications = new LinkedList<>();
        communications.add(communication0);
        communications.add(communication1);
        communications.add(communication2);

        for(Communication communication:communications){
            communication.setId(generateId());
            communication.setTimeSeries(generateSeries());
            communication.setStatusInformations(generateInfos());
            setRevision(communication);
        }

        final System system = new System();

        List<ServiceInstance> allServiceInstances = new LinkedList<ServiceInstance>();
        allServiceInstances.addAll(serviceInstances0);
        allServiceInstances.addAll(serviceInstances1);
        allServiceInstances.addAll(serviceInstances2);
        allServiceInstances.addAll(serviceInstances3);
        allServiceInstances.addAll(serviceInstances4);
        allServiceInstances.addAll(serviceInstances5);
        system.setServiceInstances(allServiceInstances);

        List<Node> allNodes = new LinkedList<>();
        allNodes.addAll(nodes0);
        allNodes.addAll(nodes1);
        allNodes.addAll(nodes2);
        system.setNodes(allNodes);

        List<CommunicationInstance> allCommunicationInstances = new LinkedList<>();
        allCommunicationInstances.addAll(communicationInstances0);
        allCommunicationInstances.addAll(communicationInstances1);
        allCommunicationInstances.addAll(communicationInstances2);
        system.setCommunicationInstances(allCommunicationInstances);

        for (CommunicationInstance communicationInstance : allCommunicationInstances) {
            communicationInstance.addStatusInformation(createCommunicationStatusInfo());
            communicationInstance.addStatusInformation(createInfo("usage", "nothing"));
            communicationInstance.setTimeSeries(generateSeries());
        }

        for (Communication communication : communications) {
            communication.addStatusInformation(createCommunicationStatusInfo());
            communication.addStatusInformation(createInfo("usage", "nothing"));
            communication.setTimeSeries(generateSeries());
        }




        system.setName("Name");
        system.setCommunications(communications);
        system.setNodeGroups(groups);
        system.setServices(services);

        if(id == null || entityManager.find(System.class, id) == null){
            system.setId(generateId());
        }else{
            system.setId(id);
        }



        //Save to database

        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();
        entityManager.persist(system);
        tx.commit();
        return "Dummy created with id: " + system.getId() + "\n";
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
