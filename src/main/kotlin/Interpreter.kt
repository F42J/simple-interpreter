package org.example

import java.util.stream.Collectors

class Interpreter {
    private val scopes: MutableList<HashMap<String, Int>> = mutableListOf(HashMap<String, Int>())

    fun execute(commands: List<String>): Boolean {
        for (command in commands) {
            val tokens = command.split(" ")
            val cleaned = tokens.stream().filter(String::isNotEmpty).collect(Collectors.toList())
            if (cleaned.size == 0 || cleaned.size > 4) {
                println("Invalid syntax in command: $command")
            }
            when (cleaned[0]) {
                "print" -> {
                    println(resolve(cleaned[1]))
                }

                "scope" -> {
                    if (cleaned[1] == "{" && cleaned.size == 2) {
                        scopes.add(HashMap())
                    } else {
                        return false
                    }
                }

                "}" -> {
                    if (scopes.size > 1) {
                        scopes.removeLast()
                    } else {
                        println("Closing Scope without matching opening Scope")
                        return false
                    }
                }

                else -> {
                    if (cleaned.size != 3) {
                        println("Invalid syntax in command: $command")
                        return false
                    }
                    try {
                        val value = Integer.valueOf(cleaned[2])
                        scopes[scopes.lastIndex].put(cleaned[0], value)
                    } catch (e: NumberFormatException) {
                        val value = resolve(cleaned[2])
                        if (value != null) {
                            scopes[scopes.lastIndex].put(cleaned[0], value)
                        } else {
                            println("Cannot assign null variable")
                            return false
                        }
                    }
                }
            }
        }
        return true
    }

    private fun resolve(name: String): Int? {
        for (map in scopes.reversed()) {
            if (map.containsKey(name)) {
                return map[name]
            }
        }
        return null
    }
}
