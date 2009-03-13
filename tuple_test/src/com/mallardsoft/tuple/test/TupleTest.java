package com.mallardsoft.tuple.test;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import junit.framework.TestCase;

import com.mallardsoft.tuple.End;
import com.mallardsoft.tuple.Pair;
import com.mallardsoft.tuple.Triple;
import com.mallardsoft.tuple.Tuple;
import com.mallardsoft.tuple.Variable;
import com.mallardsoft.tuple.Version;

public class TupleTest extends TestCase {

    public void testTupleHashMap() {
        Map<Pair<String, String>, Integer> population = new HashMap<Pair<String, String>, Integer>();
        population.put(
    		Tuple.from("TX", "Dallas"),
            1213825);
        population.put(
    		Tuple.from("TX", "Fort Worth"),
            624067);
        population.put(
    		Tuple.from("IL", "Springfield"),
            203564);
        population.put(
    		Tuple.from("NM", "Albuquerque"),
            494236);
        
        int p = population.get(
    		Tuple.from("TX", "Fort Worth"));
        // p = 624067
		assertEquals(624067, p);
        assertNull(population.get(
    		Tuple.from("NM", "Roswell")));
        assertNull(population.get(
    		Tuple.from("NJ", "Springfield")));
    }
    
    public void testSort() {
    	SortedSet<Pair<Character,Integer>> bingo = new TreeSet<Pair<Character,Integer>>();
    	bingo.add(Tuple.from('B',5));
    	bingo.add(Tuple.from('N', 41));
    	bingo.add(Tuple.from('I', 23));
    	bingo.add(Tuple.from('G',55));
    	bingo.add(Tuple.from('O', 69));
    	bingo.add(Tuple.from('I', 27));
    	bingo.add(Tuple.from('O', 61));
    	
    	String s = bingo.toString();
    	// s = "[(B, 5), (G, 55), (I, 23), (I, 27), (N, 41), (O, 61), (O, 69)]"
		assertEquals("[(B, 5), (G, 55), (I, 23), (I, 27), (N, 41), (O, 61), (O, 69)]", s);
    }
    
    public void testTupleToString() {
        Triple<String,String,Integer> cityPopulation = Tuple.from("NM", "Albuquerque", 494236);
        String s = cityPopulation.toString();
        // s = "(NM, Albuquerque, 494236)"
		assertEquals( "(NM, Albuquerque, 494236)", s );
    }
    
    public void testIP() {
    	String ip = Tuple.from(192, 168, 0, 100).toString("", ".", "");
    	// ip = "192.168.0.100"
    	assertEquals("192.168.0.100", ip);
    }
    
    public void testVersion() {
        Version v1 = new Version(2,0,2,3425);
        Version v2 = new Version(2,1,0,241);
        
        assertEquals("2.0.2.3425", v1.toString());
        assertEquals("2.1.0.241", v2.toString());
        assertTrue("Version 2.1.0.241 > 2.0.2.3425", v2.compareTo(v1) > 0);
    }
    
    public void testExtract() {
    	Variable<String> v1 = new Variable<String>();
    	Variable<Integer> v2 = new Variable<Integer>();
    	
    	Tuple.from("Hello", 42).extract(v1).extract(v2);
    	String e1 = v1.get();
    	int e2 = v2.get();
		assertEquals("Hello", e1);
		assertEquals(42, e2);
    }
    
    public void testGet() {
    	Pair<String, Integer> t = Tuple.from("Hello", 42);
    	String e1 = Tuple.get1(t);
    	int e2 = Tuple.get2(t);
		assertEquals("Hello", e1);
		assertEquals(42, e2);
    }
    
    public void testReturnByRef() {
    	Variable<Integer> index = new Variable<Integer>();
    	
    	if (findCharacter("Hello, world", 'w', index)) {
    		int i = index.get();
    		// i = 7
    		assertEquals(7, i);
    	}
    	else {
    		fail();
    	}
    }
    
    public void testMultiReturn() {
    	Variable<Boolean> found = new Variable<Boolean>();
    	Variable<Integer> index = new Variable<Integer>();
    	
    	findCharacter("Hello, world", 'w').extract(found).extract(index);
    	assertTrue(found.get().booleanValue());
    	if (found.get()) {
    		int i = index.get();
    		// i = 7
    		assertEquals(7, i);
    	}
    	else {
    		fail();
    	}
    }

	private Pair<Boolean, Integer> findCharacter(String string, char c) {
		int index = string.indexOf(c);
		if (index == -1)
			return Tuple.from(false, 0);
		else
			return Tuple.from(true, index);
	}
	
	private boolean findCharacter(String string, char c, Variable<Integer> indexRef) {
		int index = string.indexOf(c);
		if (index == -1)
			return false;
		else {
			indexRef.set(index);
			return true;
		}
	}
	
	public void testPrepend() {
		Tuple<Double,Tuple<Integer,Tuple<String,End>>> t1 = Tuple.from("c").prepend(2).prepend(2.3);
		Triple<Double,Integer,String> t2 = Tuple.from(2.3, 2, "c");
		assertTrue(t1.equals(t2));
	}
	
	public void testGet1() {
		String e1 = Tuple.get1(Tuple.from("Hello", 4));
		assertEquals("Hello", e1);
	}
	
	public void testGet2() {
		assertEquals(4, Tuple.get2(Tuple.from("Hello", 4)).intValue());
	}
	
	public void testGet3() {
		assertEquals(3, Tuple.get3(Tuple.from("one", 2.0, 3, "four", 5.0, 6, "seven", 8.0, 9, "ten")).intValue());
	}
	
	public void testGet10() {
		assertEquals("ten", Tuple.get10(Tuple.from("one", 2.0, 3, "four", 5.0, 6, "seven", 8.0, 9, "ten")));
	}
}
