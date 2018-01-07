package com.Utility.MachineLearningUtl;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.apache.ignite.ml.clustering.KMeansLocalClusterer;
import org.apache.ignite.ml.clustering.KMeansModel;
import org.apache.ignite.ml.math.DistanceMeasure;
import org.apache.ignite.ml.math.EuclideanDistance;
import org.apache.ignite.ml.math.Tracer;
import org.apache.ignite.ml.math.Vector;

import org.apache.ignite.ml.math.functions.Functions;
import org.apache.ignite.ml.math.impls.matrix.DenseLocalOnHeapMatrix;

/**
 * Example of using {@link KMeansLocalClusterer}.
 */
public class KmeansLocal {
    /**
     * Executes example.
     *
     * @param args Command line arguments, none required.
     */
    public static void main(String[] args) {
        // IMPL NOTE based on KMeansDistributedClustererTestSingleNode#testClusterizationOnDatasetWithObviousStructure
        System.out.println(">>> K-means local clusterer example started.");

        int ptsCnt = 10000;
        DenseLocalOnHeapMatrix points = new DenseLocalOnHeapMatrix(ptsCnt, 2); //行，列 初始化矩阵

        DatasetWithObviousStructure dataset = new DatasetWithObviousStructure(10000);

        List<Vector> massCenters = dataset.generate(points);

        EuclideanDistance dist = new EuclideanDistance();
        OrderedNodesComparator comp = new OrderedNodesComparator(dataset.centers().values().toArray(new Vector[] {}), dist);

        massCenters.sort(comp);

        KMeansLocalClusterer clusterer = new KMeansLocalClusterer(dist, 100, 1L);

        KMeansModel mdl = clusterer.cluster(points, 4);
        Vector[] resCenters = mdl.centers();
        Arrays.sort(resCenters, comp);

        System.out.println("Mass centers:");
        massCenters.forEach(Tracer::showAscii);

        System.out.println("Cluster centers:");
        Arrays.asList(resCenters).forEach(Tracer::showAscii);

        System.out.println("\n>>> K-means local clusterer example completed.");
    }

    /** */
    private static class OrderedNodesComparator implements Comparator<Vector> {
        /** */
        private final DistanceMeasure measure;

        /** */
        List<Vector> orderedNodes;

        /** */
        OrderedNodesComparator(Vector[] orderedNodes, DistanceMeasure measure) {
            this.orderedNodes = Arrays.asList(orderedNodes);
            this.measure = measure;
        }

        /** */
        private int findClosestNodeIndex(Vector v) {
            return Functions.argmin(orderedNodes, v1 -> measure.compute(v1, v)).get1();
        }

        /** */
        @Override public int compare(Vector v1, Vector v2) {
            int ind1 = findClosestNodeIndex(v1);
            int ind2 = findClosestNodeIndex(v2);

            int signum = (int)Math.signum(ind1 - ind2);

            if (signum != 0)
                return signum;

            return (int)Math.signum(orderedNodes.get(ind1).minus(v1).kNorm(2) -
                    orderedNodes.get(ind2).minus(v2).kNorm(2));
        }
    }
}