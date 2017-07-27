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

import java.util.List;


public class PackageAndCompanyPairs {

    private List<java.lang.Package> packages;
    private List<Company> companies;

    public PackageAndCompanyPairs(List<java.lang.Package> packages, List<Company> companies) {
        this.packages = packages;
        this.companies = companies;
    }

    public List<java.lang.Package> getPackages() {
        return packages;
    }

    public void setPackages(List<java.lang.Package> packages) {
        this.packages = packages;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }
}