package org.example

import java.io.File

fun main(args: Array<String>) {
    if (args.size != 1) {
        println("Usage: java -jar example.jar  <input> ")
        return
    }
    val path = args[0]
    val program = File(path)
    val interpreter = Interpreter()
    val res = interpreter.execute(program.readLines())
    if (!res) print("Interpreter terminated unexpectedly")
}