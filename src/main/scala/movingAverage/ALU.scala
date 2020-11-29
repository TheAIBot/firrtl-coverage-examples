import Chisel._
import chisel3.util
import chiseltest._
import org.scalatest._

object opcodes {
    val PLUS = 0.U
    val SUB = 1.U
    val Mul = 2.U
    val SHR = 3.U
}

class ALU() extends Module {
    val io = IO(new Bundle{
        val ena = Input(Bool())
        val op = Input(UInt(2.W))
        val a = Input(UInt(8.W))
        val b = Input(UInt(8.W))
        val out = Output(UInt(16.W))
    })

    when (io.ena) {
        switch (io.op) {
            is (opcodes.PLUS) {
                io.out := io.a + io.b
            }
            is (opcodes.SUB) {
                io.out := io.a - io.b
            }
            is (opcodes.Mul) {
                io.out := io.a * io.b
            }
            is (opcodes.SHR) {
                io.out := io.a >> io.b
            }
        }
    }
}