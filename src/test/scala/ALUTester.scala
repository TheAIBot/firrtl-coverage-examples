import chisel3._
import chiseltest._
import org.scalatest._
import chiseltest.experimental.TestOptionBuilder._
import treadle.WriteCoverageAnnotation

class MuxInput2x() extends Module {
    val io = IO(new Bundle{
        val cond = Input(Bool())
        val a = Input(UInt(8.W))
        val b = Input(UInt(8.W))
        val out = Output(UInt(16.W))
    })

    val a2x = io.a * 2.U
    val b2x = io.b * 2.U
    io.out := Mux(io.cond, a2x, b2x)
}

class ALUTester extends FlatSpec with ChiselScalatestTester with Matchers {
    it should "Coverage plus op" in {
        test(new ALU()) {
            dut => {
                dut.io.ena.poke(true.B)
                dut.io.a.poke(7.U)
                dut.io.b.poke(33.U)
                dut.io.op.poke(opcodes.PLUS)

                dut.clock.step()

                dut.io.out.expect(40.U)
            }
        }
    }

    it should "Coverage sub op" in {
        test(new ALU()) {
            dut => {
                dut.io.ena.poke(true.B)
                dut.io.a.poke(25.U)
                dut.io.b.poke(11.U)
                dut.io.op.poke(opcodes.SUB)

                dut.clock.step()

                dut.io.out.expect(14.U)
            }
        }
    }

    it should "Coverage mult op" in {
        test(new ALU()) {
            dut => {
                dut.io.ena.poke(true.B)
                dut.io.a.poke(3.U)
                dut.io.b.poke(4.U)
                dut.io.op.poke(opcodes.Mul)

                dut.clock.step()

                dut.io.out.expect(12.U)
            }
        }
    }

    it should "Coverage shift right op" in {
        test(new ALU()) {
            dut => {
                dut.io.ena.poke(true.B)
                dut.io.a.poke(8.U)
                dut.io.b.poke(1.U)
                dut.io.op.poke(opcodes.SHR)

                dut.clock.step()

                dut.io.out.expect(4.U)
            }
        }
    }

    it should "Coverage plus op not enabled" in {
        test(new ALU()) {
            dut => {
                dut.io.ena.poke(false.B)
                dut.io.a.poke(7.U)
                dut.io.b.poke(33.U)
                dut.io.op.poke(opcodes.PLUS)

                dut.clock.step()
            }
        }
    }

    it should "Coverage plus op with coverage" in {
        test(new ALU()).withAnnotations(Seq(WriteCoverageAnnotation)) {
            dut => {
                dut.io.ena.poke(true.B)
                dut.io.a.poke(7.U)
                dut.io.b.poke(33.U)
                dut.io.op.poke(opcodes.PLUS)

                dut.clock.step()

                dut.io.out.expect(40.U)
            }
        }
    }

    it should "Coverage sub op with coverage" in {
        test(new ALU()).withAnnotations(Seq(WriteCoverageAnnotation)) {
            dut => {
                dut.io.ena.poke(true.B)
                dut.io.a.poke(25.U)
                dut.io.b.poke(11.U)
                dut.io.op.poke(opcodes.SUB)

                dut.clock.step()

                dut.io.out.expect(14.U)
            }
        }
    }

    it should "Coverage mult op with coverage" in {
        test(new ALU()).withAnnotations(Seq(WriteCoverageAnnotation)) {
            dut => {
                dut.io.ena.poke(true.B)
                dut.io.a.poke(3.U)
                dut.io.b.poke(4.U)
                dut.io.op.poke(opcodes.Mul)

                dut.clock.step()

                dut.io.out.expect(12.U)
            }
        }
    }

    it should "Coverage shift right op with coverage" in {
        test(new ALU()).withAnnotations(Seq(WriteCoverageAnnotation)) {
            dut => {
                dut.io.ena.poke(true.B)
                dut.io.a.poke(8.U)
                dut.io.b.poke(1.U)
                dut.io.op.poke(opcodes.SHR)

                dut.clock.step()

                dut.io.out.expect(4.U)
            }
        }
    }

    it should "Coverage plus op not enabled with coverage" in {
        test(new ALU()).withAnnotations(Seq(WriteCoverageAnnotation)) {
            dut => {
                dut.io.ena.poke(false.B)
                dut.io.a.poke(7.U)
                dut.io.b.poke(33.U)
                dut.io.op.poke(opcodes.PLUS)

                dut.clock.step()
            }
        }
    }
}

class MuxTester extends FlatSpec with ChiselScalatestTester with Matchers {
    it should "Coverage mux false condition" in {
        test(new MuxInput2x()).withAnnotations(Seq(WriteCoverageAnnotation)) {
            dut => {
                dut.io.cond.poke(false.B)
                dut.io.a.poke(22.U)
                dut.io.b.poke(9.U)

                dut.clock.step()

                dut.io.out.expect(18.U)
            }
        }
    }

    it should "Coverage mux true condition" in {
        test(new MuxInput2x()).withAnnotations(Seq(WriteCoverageAnnotation)) {
            dut => {
                dut.io.cond.poke(true.B)
                dut.io.a.poke(22.U)
                dut.io.b.poke(9.U)

                dut.clock.step()

                dut.io.out.expect(44.U)
            }
        }
    }
}