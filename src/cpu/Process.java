import java.util.*;

public class Process {
	Scanner sc = new Scanner(System.in);
    public static Vector<String> processName = new Vector<String>();
    public static ArrayList<Integer> arrivalTime = new ArrayList<Integer>(); 
    public static ArrayList<Integer> burstTime = new ArrayList<Integer>();
       
    public static Map<Integer, Integer> burstTimeM = new HashMap<>();
    public static Map<Integer, String> priorityPNameM = new HashMap<>();
    
    String processNameS = "";
    int  arrivalTimeS = 0;
    int burstTimeS = 0;
    static int NumberOfProcesses;
    static int minOfBT = 0;
    static int priority = 0;
   public static Vector<Integer> priorityV = new Vector<Integer>();
    Scanner input = new Scanner(System.in);

    public Process(){}
    public Process(int NumberOfProcesses)
    {
        Process.NumberOfProcesses = NumberOfProcesses;
        
        for(int i=0 ;i<NumberOfProcesses ;i++)
        {
            System.out.print("Enter process number " + (i) + " Name : ");
            processNameS = input.next();
            processName.add(processNameS);
            System.out.print("Enter process number " + (i) + " Arrival Time : ");
            arrivalTimeS  = input.nextInt();
            arrivalTime.add(arrivalTimeS);
            System.out.print("Enter process number " + (i) + " Burst Time : ");
            burstTimeS = input.nextInt();
            burstTime.add(burstTimeS);
            
                System.out.print("Enter priority of " + Process.processName.get(i) + ": ");
                priority = sc.nextInt();
                priorityV.add(priority);
                
            
            burstTimeM.put(priority, burstTimeS);
            priorityPNameM.put(priority, processNameS);
        }
    }
    
    public int Technique()
    {
        int answer;
        System.out.println("Choose Ur ScheduLling Technique:-)");
        System.out.println("1)SJF\n2)SRTF\n3)Priority\n4)AG");
        answer = input.nextInt();
        return answer;
    }
    
    
    
    public static int min(List<Integer> list) {
    	int minIndex = list.indexOf(Collections.min(list).intValue());
    	return list.get(minIndex);
    	
    	
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        @SuppressWarnings("resource")
        
		Scanner in = new Scanner(System.in);
        
        
        Process P = new Process(4);
        int num = P.Technique();
		CPU S = new CPU(num);
		minOfBT = min(burstTime);
		
		S.minOfBT = minOfBT;

    }
}
