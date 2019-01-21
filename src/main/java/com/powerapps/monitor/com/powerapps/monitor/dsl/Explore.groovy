package com.powerapps.monitor.com.powerapps.monitor.dsl

class Explore {

    void processString() {
        def x = "This is groovy"
        int len = x.length()
        println len
    }

    void processList() {
        def customers = [0, -1, -199, 1, 2, 3, 4, 5, 9]

        println(customers.get(4))
    }

    void 'list processing'(){
        def customers = ["joshua","zoe","andrew","sam","john"]
        println customers.getClass()

    }


}
