package com.powerapps.scripts
def x = 104
println x.getClass()
x = "Guru99"
println x.getClass()


def multilinestring = """Groovy
at
Guru99"""
println multilinestring


def y = ["Guru99", "is", "Best", "for", "Groovy"]
println y
y.add("Learning")
println(y.contains("is"))
println(y.get(2))
println(y.pop())