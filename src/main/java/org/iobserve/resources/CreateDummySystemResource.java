package org.iobserve.resources;

import org.iobserve.models.*;
import org.iobserve.models.System;
import org.iobserve.models.util.RevisionedBean;
import org.iobserve.models.util.SeriesElement;
import org.iobserve.models.util.StatusInfo;
import org.iobserve.models.util.TimeSeries;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import javax.persistence.EntityTransaction;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Created by cdor on 03.06.16.
 */
@Path("v1/systems")
public class CreateDummySystemResource {
    @Inject
    private EntityManager entityManager;

    @POST
    @Path("/createDummy")
    public String createDummy() {
        createBoostrapData(null);
        return "dummy created";
    }

    @POST
    @Path("/createDummy/{id}")
    public String createDummyWithId(@PathParam("id") String id) {
        String message = createBoostrapData(id);
        return message;
    }

    private String createBoostrapData(String id){
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
}
