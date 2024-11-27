import org.example.Interpreter
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals

class InterpreterTest {
    @Test
    fun testValueAssignment1() {
        val sysout = System.out
        val outstream = ByteArrayOutputStream()
        val teststream = PrintStream(outstream)
        val commands = listOf(
            "x = 5",
            "y = 3",
            "z = 0",
            "print z",
            "print x",
            "print y"
        )
        val interpreter = Interpreter()
        try {
            System.setOut(teststream)
            val result = interpreter.execute(commands)
            assert(result)
            teststream.flush()
            val captured = outstream.toString()
            assertEquals(
                "0\n5\n3\n", captured
            )
        } finally {
            System.setOut(sysout)
        }
    }

    @Test
    fun testValueAssignment2() {
        val sysout = System.out
        val outstream = ByteArrayOutputStream()
        val teststream = PrintStream(outstream)
        val commands = listOf(
            "x = -1",
            "y = -100",
            "z = 9999",
            "print z",
            "print x",
            "print a",
            "print y"
        )
        val interpreter = Interpreter()
        try {
            System.setOut(teststream)
            val result = interpreter.execute(commands)
            assert(result)
            teststream.flush()
            val captured = outstream.toString()
            assertEquals(
                "9999\n-1\nnull\n-100\n", captured
            )
        } finally {
            System.setOut(sysout)
        }
    }

    @Test
    fun testValueReassignment() {
        val sysout = System.out
        val outstream = ByteArrayOutputStream()
        val teststream = PrintStream(outstream)
        val commands = listOf(
            "x = 5",
            "y = 3",
            "z = 0",
            "y = x",
            "a = z",
            "print z",
            "print x",
            "print y",
            "print a"
        )
        val interpreter = Interpreter()
        try {
            System.setOut(teststream)
            val result = interpreter.execute(commands)
            assert(result)
            teststream.flush()
            val captured = outstream.toString()
            assertEquals(
                "0\n5\n5\n0\n", captured
            )
        } finally {
            System.setOut(sysout)
        }
    }

    @Test
    fun testMultiletterNames() {
        val sysout = System.out
        val outstream = ByteArrayOutputStream()
        val teststream = PrintStream(outstream)
        val commands = listOf(
            "abc = 5",
            "def = 3",
            "ghi = 0",
            "print ghi",
            "print abc",
            "print def"
        )
        val interpreter = Interpreter()
        try {
            System.setOut(teststream)
            val result = interpreter.execute(commands)
            assert(result)
            teststream.flush()
            val captured = outstream.toString()
            assertEquals(
                "0\n5\n3\n", captured
            )
        } finally {
            System.setOut(sysout)
        }
    }

    @Test
    fun testScopeSimple() {
        val sysout = System.out
        val outstream = ByteArrayOutputStream()
        val teststream = PrintStream(outstream)
        val commands = listOf(
            "x = 5",
            "print x",
            "scope {",
            "y = 1",
            "print y",
            "}",
            "print x",
            "print y"
        )
        val interpreter = Interpreter()
        try {
            System.setOut(teststream)
            val result = interpreter.execute(commands)
            assert(result)
            teststream.flush()
            val captured = outstream.toString()
            assertEquals(
                "5\n1\n5\nnull\n", captured
            )
        } finally {
            System.setOut(sysout)
        }
    }

    @Test
    fun testValueShadowing() {
        val sysout = System.out
        val outstream = ByteArrayOutputStream()
        val teststream = PrintStream(outstream)
        val commands = listOf(
            "x = 5",
            "print x",
            "scope {",
            "x = 1",
            "print x",
            "scope {",
            "x = 3",
            "print x",
            "scope {",
            "print x",
            "}",
            "print x",
            "}",
            "print x",
            "}",
            "print x"
        )
        val interpreter = Interpreter()
        try {
            System.setOut(teststream)
            val result = interpreter.execute(commands)
            assert(result)
            teststream.flush()
            val captured = outstream.toString()
            assertEquals(
                "5\n1\n3\n3\n3\n1\n5\n", captured
            )
        } finally {
            System.setOut(sysout)
        }
    }

    @Test
    fun testMismatchedScopes() {
        val sysout = System.out
        val outstream = ByteArrayOutputStream()
        val teststream = PrintStream(outstream)
        val commands = listOf(
            "x = 5",
            "print x",
            "scope {",
            "x = 1",
            "print x",
            "}",
            "print x",
            "}",
            "print x"
        )
        val interpreter = Interpreter()
        try {
            System.setOut(teststream)
            interpreter.execute(commands)
            teststream.flush()
            val captured = outstream.toString()
            assertEquals(
                "5\n1\n5\nClosing Scope without matching opening Scope\n", captured
            )
        } finally {
            System.setOut(sysout)
        }
    }

    @Test
    fun testInvalidSyntax() {
        val sysout = System.out
        val outstream = ByteArrayOutputStream()
        val teststream = PrintStream(outstream)
        val commands = listOf(
            "x = 5",
            "print x",
            "scope {",
            "x=1",
            "print x",
            "}",
            "print x",
            "}",
            "print x"
        )
        val interpreter = Interpreter()
        try {
            System.setOut(teststream)
            interpreter.execute(commands)
            teststream.flush()
            val captured = outstream.toString()
            assertEquals(
                "5\nInvalid syntax in command: x=1\n", captured
            )
        } finally {
            System.setOut(sysout)
        }
    }
}