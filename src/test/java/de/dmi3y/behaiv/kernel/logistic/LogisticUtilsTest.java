package de.dmi3y.behaiv.kernel.logistic;

import de.dmi3y.behaiv.kernel.KernelTest;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.simple.SimpleMatrix;
import org.junit.Test;

import java.util.Random;

import static de.dmi3y.behaiv.kernel.KernelTest.HOME;
import static de.dmi3y.behaiv.kernel.KernelTest.JOG;
import static de.dmi3y.behaiv.kernel.KernelTest.WORK;
import static junit.framework.TestCase.assertEquals;

public class LogisticUtilsTest {

    public static SimpleMatrix SINGLE_VALUE_MATRIX = new SimpleMatrix(new double[][]{new double[]{10.0}});
    public static SimpleMatrix MULTIPLE_VALUE_MATRIX = new SimpleMatrix(new double[][]{new double[]{10.0, 1.0, 4.0, 6.0, 9.0, 2.0}}).transpose();

    @Test
    public void sigmoid_feed10_receive0_99995() {
        final SimpleMatrix zigmoid = LogisticUtils.sigmoid(new SimpleMatrix(new double[][]{new double[]{10.0}}));
        assertEquals(0.9999546021312976, zigmoid.get(0, 0));
    }


    @Test
    public void verify_1minusM_equalsTo_negativeMPlus1() {
        final SimpleMatrix plus = SINGLE_VALUE_MATRIX.negative().plus(1);
        assertEquals(-9.0, plus.get(0, 0));
    }


    @Test
    public void costFunction_feedMatrix_receiveCorretResult() {
        final SimpleMatrix x = SINGLE_VALUE_MATRIX;
        final SimpleMatrix theta = new SimpleMatrix(new double[][]{new double[]{0.01}});
        final SimpleMatrix y = new SimpleMatrix(new double[][]{new double[]{1.0}});
        final SimpleMatrix cost = LogisticUtils.costFunction(x, theta, y);
        System.out.println(cost.toString());
    }

    @Test
    public void gradientDescent_feedMatrix_receiveCorrectResult() {

        for (int i = 0; i < 100; i++) {
            SimpleMatrix x = new SimpleMatrix(KernelTest.getTrainingArray()); //14x3
            final SimpleMatrix y = new SimpleMatrix(KernelTest.getLabelsArray());//14x4
            Random rand = new Random();
            SimpleMatrix theta = SimpleMatrix.random_DDRM(3, 4, 0, 1, rand); //3x4?

            for (int j = 0; j < 10000; j++) {
                theta = LogisticUtils.gradientDescent(x, theta, y, 0.1);
            }

            x = new SimpleMatrix(new double[][]{
                    new double[]{
                            (16 * 60 + 30.0) / (24 * 60),
                            WORK[0],
                            WORK[1]}
            });
            theta.print("%f");
            SimpleMatrix result = LogisticUtils.sigmoid(x.mult(theta));
            double max = CommonOps_DDRM.elementMax(result.getDDRM());
            assertEquals(max, result.get(0, 2));

            x = new SimpleMatrix(new double[][]{
                    new double[]{
                            (5 * 60 + 10.0) / (24 * 60),
                            HOME[0],
                            HOME[1]}
            });
            result = LogisticUtils.sigmoid(x.mult(theta));
//            result.print("%f");
            max = CommonOps_DDRM.elementMax(result.getDDRM());

            assertEquals(max, result.get(0, 0));


//            theta.print("%f");
            x = new SimpleMatrix(new double[][]{
                    new double[]{
                            (19 * 60 + 15.0) / (24 * 60),
                            JOG[0],
                            JOG[1]}
            });
            result = LogisticUtils.sigmoid(x.mult(theta));
//            result.print("%f");
            max = CommonOps_DDRM.elementMax(result.getDDRM());

            assertEquals(max, result.get(0, 1));

            System.out.println("\n");
//            theta.print("%f");
        }
    }
}