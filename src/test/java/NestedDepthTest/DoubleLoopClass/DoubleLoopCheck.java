package NestedDepthTest.DoubleLoopClass;

public class DoubleLoopCheck 
{
    public static void main(String[] args) 
    {
        boolean notfalse = true;
        boolean x = true;
        boolean y = true;
        while(!notfalse)
        {
        for(int i = 0; i < 5; i++)
            {
            
                while (!x) 
                    {
                        
                }
            }
        }

        System.out.println("This is a line break");

        for(int j = 0; j < 5; j++)
        {
            while(!y)
            {
                for(int k = 0; k<5; k++)
                {
                    for(int z = 0; z<5; z++)
                    {
                        
                    }
                }
            }
        }
    }

}
