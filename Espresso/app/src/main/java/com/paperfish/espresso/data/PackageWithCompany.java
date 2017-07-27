
package com.paperfish.espresso.data;

public class PackageWithCompany {

    private Package pkg;
    private Company company;

    public PackageWithCompany(Package p, Company c) {
        this.pkg = p;
        this.company = c;
    }

    public Package getPkg() {
        return pkg;
    }

    public void setPkg(Package pkg) {
        this.pkg = pkg;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
