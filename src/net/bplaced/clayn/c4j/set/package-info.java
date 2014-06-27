/**
 * This package contains all build in implementations from the {@link net.bplaced.clayn.c4j.Setting} class. 
 * Other implementations may be created by yourself.  Most of the implementations 
 * found in this package are very simple due to the Classes for which the settings 
 * are have simple ways to be parsed from Strings. All classes that are able to be 
 * parsed to and from a String will be able to be an implementation and also be used 
 * by {@link net.bplaced.clayn.c4j.Configuration} and {@link net.bplaced.clayn.c4j.Environment}. 
 * <br><br>
 * <h2>Note:</h2><br>
 * If you want to implement your own Settings that can be used with the 
 * {@link net.bplaced.clayn.c4j.anno.Configure} annoation
 * you have to make sure to provide 
 * a constructor which has only one String as argument.
 * @since 0.1
 */
package net.bplaced.clayn.c4j.set;