/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nhskidneyregister;

/**
 *
 * @author Nick Dalton
 */
public class NHSKidneyRegister
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        //KidneyPaitentRegisterTester.runTests(); 
         System.out.println("------------------------------");
        KidneyPaitentRegisterTester.runInternalChecks(); 
        System.out.println("------------------------------");
        KidneyPaitentRegisterTester.runSpeedTests( );
    }
    
}
