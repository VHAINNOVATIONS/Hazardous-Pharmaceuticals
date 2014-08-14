As the source code and libraries are (as checked-in by SwRI), the build is only
capable of creating development ear file.  The steps below will be needed to
create an ear file capable of running in either a VA testing, pre-production or
production environment.

Environment definitions will be defined as follows, these definitions are used
to determine if a given step is required to produce a workable ear file for the
environment desired:
    ALL     - Each target environment will be required to execute this step
    TESTING - Step is only required for the testing environment.  This only
              affects the usage of the STS service.
    PROD    - Step is required for both of the production environments.
    PROD32  - Step is required for production environment using FDB-DIF 3.2.2
    PROD33  - Step is required for production environment using FDB-DIF 3.3

1) ALL:
    # copy the kaajeeConfig.xml.prod (production version)
    cp -av ${BASE}/PS_PPS_Dev/PS_PPS_ui/web/WEB-INF/kaajeeConfig.xml.prod ${BASE}/PS_PPS_Dev/PS_PPS_ui/web/WEB-INF/kaajeeConfig.xml
    # copy the weblogic.xml (production version)
    cp -av ${BASE}/PS_PPS_Dev/PS_PPS_ui/etc/web/national/weblogic.xml.prod ${BASE}/PS_PPS_Dev/PS_PPS_ui/etc/web/national/weblogic.xml
    
2a) TESTING:
    # copy the testing STS library for testing
    cp -av ${BASE}/PS_PPS_Bld/PS_PPS_lib/Interface/sts/StsConnectivity3.jar.dev ${BASE}/PS_PPS_Bld/PS_PPS_lib/Interface/sts/StsConnectivity3.jar
    # remove the production STS library
    rm -vf ${BASE}/PS_PPS_Bld/PS_PPS_lib/Interface/sts/stsProd.jar
2b) PROD:
    # copy the production STS library
    cp -av ${BASE}/PS_PPS_Bld/PS_PPS_lib/Interface/sts/stsProd.jar.prod ${BASE}/PS_PPS_Bld/PS_PPS_lib/Interface/sts/stsProd.jar
    # remove the testing STS library
    rm -vf ${BASE}/PS_PPS_Bld/PS_PPS_lib/Interface/sts/StsConnectivity3.jar

3a) PROD32
    # copy the FDB-DIF 3.2.2 library
    cp -av ${BASE}/PS_PPS_Bld/PS_PPS_lib/Interface/drugdatavendor/fdb/fdbdif32-3.2.2.jar.prod-alt ${BASE}/PS_PPS_Bld/PS_PPS_lib/Interface/drugdatavendor/fdb/fdbdif32-3.2.2.jar
    # remove the FDB-DIF 3.3 library
    rm -vf ${BASE}/PS_PPS_Bld/PS_PPS_lib/Interface/drugdatavendor/fdb/DIF3.3.jar
3b) PROD33
    # copy the FDB-DIF 3.3 library
    cp -av ${BASE}/PS_PPS_Bld/PS_PPS_lib/Interface/drugdatavendor/fdb/DIF3.3.jar.prod-alt ${BASE}/PS_PPS_Bld/PS_PPS_lib/Interface/drugdatavendor/fdb/DIF3.3.jar
    # remove the FDB-DIF 3.2.2 library
    rm -vf ${BASE}/PS_PPS_Bld/PS_PPS_lib/Interface/drugdatavendor/fdb/fdbdif32-3.2.2.jar

4) ALL:
    # run the ant build script, passing in the following arguments [properties]:
    #  e.g.:  ant -Ddeploy.environment=t1rack -Ddatabase.url.port=1521 -Ddatabase.url.port.suffix=:  ear
    -Ddeploy.environment=t1rack
    -Ddatabase.url.port=1521
    -Ddatabase.url.port.suffix=:
