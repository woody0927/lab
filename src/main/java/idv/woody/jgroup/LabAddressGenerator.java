package idv.woody.jgroup;

import org.jgroups.Address;
import org.jgroups.stack.AddressGenerator;
import org.jgroups.util.TopologyUUID;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by chun-chiao on 2016/5/8.
 */
@Component
@Scope("singleton")
public class LabAddressGenerator implements AddressGenerator {
    private String siteID = "";
    private String rackID = "";
    private String machineID = "";
    private String logicalName = "";

    public LabAddressGenerator() {
    }

    public String getSiteID() {
        return this.siteID;
    }

    public void setSiteID(String siteID) {
        this.siteID = siteID;
    }

    public String getRackID() {
        return this.rackID;
    }

    public void setRackID(String rackID) {
        this.rackID = rackID;
    }

    public String getMachineID() {
        return this.machineID;
    }

    public void setMachineID(String machineID) {
        this.machineID = machineID;
    }

    public String getLogicalName() {
        return this.logicalName;
    }

    public void setLogicalName(String logicalName) {
        this.logicalName = logicalName;
    }

    public Address generateAddress() {
        return TopologyUUID.randomUUID(this.logicalName, this.siteID, this.rackID, this.machineID);
    }
}
