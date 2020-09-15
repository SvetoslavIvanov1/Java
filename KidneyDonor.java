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
public class KidneyDonor extends AbstractKidneyPerson
{

    public KidneyDonor(String thefullName, String theNHSPatientID2017,
                        String theaddress , int blood, int kidney)
    {
        super(thefullName, theNHSPatientID2017, theaddress ,  blood,  kidney);
    }
}
