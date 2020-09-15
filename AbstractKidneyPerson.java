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
public class AbstractKidneyPerson extends NHSPatient
{
    
    protected int bloodType;
    protected int kidneyType;

    public AbstractKidneyPerson(String thefullName,
                                String theNHSPatientID2017, 
                                String theaddress, 
                                int blood, int kidney )
    {
        super(thefullName, theNHSPatientID2017, theaddress);
        this.bloodType = blood ; 
        this.kidneyType = kidney ; 
    }
    
    /**
     * @return the bloodType
     */
    public int getBloodType()
    {
        return bloodType;
    }

    /**
     * @param bloodType the bloodType to set
     */
    public void setBloodType(int bloodType)
    {
        this.bloodType = bloodType;
    }

    /**
     * @return the kidneyType
     */
    public int getKidneyType()
    {
        return kidneyType;
    }

    /**
     * @param kidneyType the kidneyType to set
     */
    public void setKidneyType(int kidneyType)
    {
        this.kidneyType = kidneyType;
    }

    @Override
    public String toString()
    {
        return super.toString() + "|" + bloodType + "|" + kidneyType;
    }
    
}
