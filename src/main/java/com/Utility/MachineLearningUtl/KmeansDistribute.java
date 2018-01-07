package com.Utility.MachineLearningUtl;

import java.io.IOException;
import java.util.Arrays;
import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.ml.clustering.KMeansDistributedClusterer;
import org.apache.ignite.ml.math.EuclideanDistance;
import org.apache.ignite.ml.math.StorageConstants;
import org.apache.ignite.ml.math.Tracer;
import org.apache.ignite.ml.math.Vector;
import org.apache.ignite.ml.math.impls.matrix.SparseDistributedMatrix;
import org.apache.ignite.thread.IgniteThread;


public class KmeansDistribute {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(">>> K-means distributed clusterer example started.");
        String fileName = args[1];
        int kind = Integer.parseInt(args[2]);
        try (Ignite ignite = Ignition.start("gridgain/examples/config/example-ignite.xml")) {
            System.out.println(">>> Ignite grid started.");

            IgniteThread igniteThread;
            igniteThread = new IgniteThread(ignite.configuration().getIgniteInstanceName(),
                    KmeansDistribute.class.getSimpleName(), () -> {
                ReadFile df = null;
                try {
                    df = new ReadFile(fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int rowNum = df.getRowNum();
                int colNum = df.getColNum();

                SparseDistributedMatrix points = new SparseDistributedMatrix(rowNum, colNum,
                        StorageConstants.ROW_STORAGE_MODE, StorageConstants.RANDOM_ACCESS_MODE);

                try {
                    df.write(points);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                EuclideanDistance dist = new EuclideanDistance();
                KMeansDistributedClusterer clusterer = new KMeansDistributedClusterer(dist, 3, 100, 1L);
                Vector[] resCenters = clusterer.cluster(points, kind).centers();
                System.out.println("Cluster centers:");
                Arrays.asList(resCenters).forEach(Tracer::showAscii);
                points.destroy();
                System.out.println("\n>>> K-means distributed clusterer example completed.");
            });

            igniteThread.start();
            igniteThread.join();
        }

    }
}