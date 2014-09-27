/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lordmat.githubstream.data.checker;

/**
 *
 * @author mat
 */
public class BranchChecker extends AbstractChecker{

    
    
    public BranchChecker() {
        super(300_000); // 5 minutes
    }
    
    
    

    @Override
    protected void query() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
    
    
}
