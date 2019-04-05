package model;

import java.util.ArrayList;

public class OrgList {
    private ArrayList<Org> orgList;

    public OrgList(ArrayList<Org> orgList) {
        this.orgList = orgList;
    }

    public ArrayList<Org> getOrgList() {
        return orgList;
    }

    public void setOrgList(ArrayList<Org> orgList) {
        this.orgList = orgList;
    }

    public void add(Org org) {
        orgList.add(org);
    }

    public Org findOrg(String name) {
        for (Org org : orgList)
            if (org.getName().toLowerCase().equals(name.toLowerCase()))
                return org;
        return null;
    }

    @Override
    public String toString() {
        return "OrgList{" +
                "orgList=" + orgList +
                '}';
    }

    public void addAllOrgs(ArrayList<Org> new_orgs) {
        for(Org org : new_orgs)
            if(findOrg(org.getName()) == null)
                orgList.add(org);
    }
}
