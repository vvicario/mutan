package mutan.services;

import mutan.domain.Sequence;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import mutan.MutanApplication;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author vvicario
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MutanApplication.class}, loader = AnnotationConfigContextLoader.class)
@EnableAutoConfiguration
public class MutantServiceTest {

    @Autowired
    private MutanServiceImpl service;

    String[] dnaList = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
    String[] dnaList2 = {"ATGCGA", "AAAACC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
    String[] dnaList3 = {"ATGCGA", "CTGTGA", "TTATGA", "ATAAGA", "CTCCTA", "TTTTTG"};
    String[] dnaList4 = {"AGGCGA", "CAGTGC", "TAAGGT", "AAAAGG", "CCCCTA", "TTTTAA"};
    String[] dnaList5 = {"TTGCGA", "CAGTGC", "GTATGT", "AGTAGG", "CCTGTA", "TCACTG"};

    @Test
    public void testIsMutanDiagonalAndHorizontalAndVertical() throws ExecutionException, InterruptedException {
        Sequence sequence = new Sequence();
        sequence.setDna(dnaList);
        CompletableFuture<Boolean> isMutant = service.isMutant(sequence);
        Assert.assertTrue(isMutant.get());
    }

    @Test
    public void testIsMutantHorizontal() throws ExecutionException, InterruptedException {
        Sequence sequence = new Sequence();
        sequence.setDna(dnaList2);
        CompletableFuture<Boolean> isMutant = service.isMutant(sequence);
        Assert.assertTrue(isMutant.get());
    }

    @Test
    public void testIsMutantHorizontalAndVertical() throws ExecutionException, InterruptedException {
        Sequence sequence = new Sequence();
        sequence.setDna(dnaList3);
        CompletableFuture<Boolean> isMutant = service.isMutant(sequence);
        Assert.assertTrue(isMutant.get());
    }

    @Test
    public void testIsMutanVerticalAndLowerDiagonal() throws ExecutionException, InterruptedException {
        Sequence sequence = new Sequence();
        sequence.setDna(dnaList4);
        CompletableFuture<Boolean> isMutant = service.isMutant(sequence);
        Assert.assertTrue(isMutant.get());
    }

    @Test
    public void testIsNotMutan()throws ExecutionException, InterruptedException {
        Sequence sequence = new Sequence();
        sequence.setDna(dnaList5);
        CompletableFuture<Boolean> isMutant = service.isMutant(sequence);
        Assert.assertFalse(isMutant.get());
    }
}

