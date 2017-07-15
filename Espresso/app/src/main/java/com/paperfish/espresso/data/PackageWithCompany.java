/*
 *  Copyright(c) 2017 lizhaotailang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.paperfish.espresso.data;

public class PackageWithCompany {

    private java.lang.Package pkg;
    private Company company;

    public PackageWithCompany(java.lang.Package p, Company c) {
        this.pkg = p;
        this.company = c;
    }

    public java.lang.Package getPkg() {
        return pkg;
    }

    public void setPkg(java.lang.Package pkg) {
        this.pkg = pkg;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
