import java.util.*;

public class CPU extends Process{
    
    Process P = new Process() {};
    
    public ArrayList<Integer> completionTime = new ArrayList<Integer>(); 
    public ArrayList<Integer> turnAroundTime = new ArrayList<Integer>();
    public ArrayList<Integer> waitingTime = new ArrayList<Integer>();


    public ArrayList<Integer> arrivalTime = new ArrayList<Integer>(); 
    public static ArrayList<Integer> burstTime = new ArrayList<Integer>();
    
   
    
    int processNumber = 0;
    
    int minOfBT = Process.min(Process.burstTime);
    int[] start = new int[Process.NumberOfProcesses];
    int[] end = new int[Process.NumberOfProcesses];
    
    Vector<Integer> rest = new Vector<Integer>();
    
    String[] resName = new String[Process.NumberOfProcesses];
    int[]    resNumber = new int[Process.NumberOfProcesses];
    
    Vector<String> resNameV = new Vector<String>();
    Vector<Integer> resNumberV = new Vector<Integer>();
    
    
   
   Vector<Integer> startV = new Vector<Integer>();
   Vector<Integer> endV = new Vector<Integer>();
    public CPU(int techNum)
    {    while(true) { 
        		switch(techNum)
        		{
        			case 1:
        				SJF();
        				break;
        			case 2:
        				SRTF();
        				break;
        			case 3:
        				Priority();
        				break;
        			case 4:
        				AG();
        				break;	
        			default:	
        				System.out.println("Invalid Number :(");
        				break;
        		}
        		break;
    }
    }

    CPU(){
    
    	
    }
    
    public void SJF()
    {
    	Arrays.fill(resNumber, -1);
    	
         for(int i=0 ; i < Process.NumberOfProcesses ; i++)
         {
        	 if(i == 0) {
        		 start[i] = Process.arrivalTime.get(i);
        		 end[i] = start[i] + Process.burstTime.get(i);
        		 completionTime.add(largest(end));
        		 resNameV.add(Process.processName.elementAt(i));
        		 resNumber[i] = end[i];
        		 resNumberV.add(end[i]);
        	 }else {
        		 int max = Collections.max(resNumberV);
        		 minOfBT = Process.min(Process.burstTime);
        		 if(Process.burstTime.get(i) == minOfBT) {
        			 start[i] = completionTime.get(end[i]);
            		 end[i] = start[i] + Process.burstTime.get(i);
            		 resNumberV.add(end[i]);
            		 completionTime.add(largest(end));
            		 resNameV.add(Process.processName.elementAt(i));
            		 
            		 resNumber[i] = largest(end);
            		 
        		 }
        		 else {
        			 
         			rest.add(Process.burstTime.get(i)); 
         			start[i] = -1;	
         			end[i] = -1;
         		 }
        	 }
         }
         Collections.sort(rest); 
         minOfBT = rest.elementAt(0);
         
         for(int p = 0; p < Process.NumberOfProcesses; p++) {
        	 if(start[p] == -1) {
        		 start[p] = largest(resNumber);
        		 end[p] = start[p] + Process.burstTime.get(p);
        		 resNumberV.add(end[p]);
        		 completionTime.add(largest(end));//resNumber[p] + minOfBT
        		 resNameV.add(Process.processName.elementAt(p));
        		 
        		 
        		 while(true) {
        			 if(p >=  Process.NumberOfProcesses) {
        				 break;
        			 }
        		 else if(resNumber[p] == -1) {
            			 resNumber[p] = largest(end);
            		 }
        			 
            		 else {
            			 p++;
            			 break;
            		 }
        		 }
        	 }else {
        		 continue;
        	 }
        	 
         }
         
         calcSJF();
         
    }
    
    
   public void SRTF()
    {	 
         for(int i=0 ; i < P.NumberOfProcesses ; i++)
         {
             
         }

    }
      
      public void Priority()
    {
    	 int calcMinV = Collections.min(Process.priorityV);
         
         for(int j = 0; j < Process.NumberOfProcesses; j++) {
        	 if(j == 0) {
        		 start[j] = Process.arrivalTime.get(j);
        		 startV.add(Process.arrivalTime.get(j));
        		 end[j] = Process.burstTime.get(0);
        		 endV.add(Process.burstTime.get(0));//
        		 completionTime.add(largest(end));
        		 resNameV.add(Process.processName.elementAt(j));
        		 resNumber[j] = end[j];
        		 resNumberV.add(end[j]);
        		 priorityV.remove(0);
        	 }
        	 else {
        		 
        		 while(true) {
        			 if(Process.priorityV.size() == 0) {
        				// j = Process.NumberOfProcesses - 1;
        				 break;
        			 }else {
        				  calcMinV = Collections.min(Process.priorityV);
        			 }
        		 if(j >= Process.NumberOfProcesses || j >= Process.priorityV.size()) {j = 0;}
        		 else if(Process.priorityV.elementAt(j) == calcMinV) {
        			 start[j] = largest(end);
        			 startV.add(Collections.max(endV));
            		 end[j] = start[j] + Process.burstTimeM.get(calcMinV);
            		 endV.add(Collections.max(endV) + burstTimeM.get(calcMinV));
            		 resNumberV.add(end[j]);
            		 completionTime.add(largest(end));
            		 //resNameV.add(Process.processName.elementAt(j));
            		 resNameV.add(priorityPNameM.get(calcMinV));
            		 
            		 resNumber[j] = largest(end);
            		 Process.priorityV.remove(j);
        		 }else {
        			 j++;
        		 }
        		 
        		 }
        		 
        	 }
         }
        
         calcPriority();       
         

    }
   
    public void AG()
    {
        int quantumTime;
        System.out.print("Enter Quantum value : ");
        quantumTime = input.nextInt();
        int TotalNumberOfProcesses = 0;
        //------------------------------
        double quan = quantumTime;//******************************

        for (int i = 0; i < Process.NumberOfProcesses; i++) {
            processes.get(i).agFactor = processes.get(i).ArrivalTime + processes.get(i).BurstTime + processes.get(i).Priority;
            processes.get(i).quantum = quan;
        }
        int prio = 10000, pos = 0;
        int count = 0, rep = 0;
        int current;
        boolean val = true;//non-preemptive
        double q, tempq = 0;

        while (TotalNumberOfProcesses != numOfProcesses) {
            current = 0;
            for (int i = count; i < processes.size(); i++) {
                if (processes.get(i).ArrivalTime <= OriginalTime) {
                    readyque.add(processes.get(i));
                    rep++;
                }
            }
            count = rep;
            if (readyque.isEmpty()) {
                OriginalTime++;
            } else {
                if (val) { // if non-preemptive
                    q = Math.ceil(readyque.get(current).quantum / 2);
                    System.out.println("Quantum (" + readyque.get(current).quantum + ") --> Ceil(50%) = (" + q + ") " + readyque.get(current).name + " Running");
                    tempq = readyque.get(current).quantum;
                    if (readyque.get(current).BurstTime >= q) {
                        timeofproc.add(OriginalTime);
                        procname.add(readyque.get(current).name);
                        readyque.get(current).BurstTime -= (int) q;
                        readyque.get(current).quantum -= (int) q;
                        OriginalTime += (int) q;
                        val = false;
                    } else { // Scenario 4
                        timeofproc.add(OriginalTime);
                        procname.add(readyque.get(current).name);
                        OriginalTime += readyque.get(current).BurstTime;
                        processes.get(readyque.get(current).ProcessID).CompletionTime = OriginalTime;
                        processes.get(readyque.get(current).ProcessID).TurnAroundTime = OriginalTime - readyque.get(current).ArrivalTime;
                        processes.get(readyque.get(current).ProcessID).WaitingTime = processes.get(readyque.get(current).ProcessID).TurnAroundTime - processes.get(readyque.get(current).ProcessID).BurstTime;
                        readyque.remove(current);
                        TotalNumberOfProcesses++;
                        prio = 10000;
                    }
                    continue;
                } // revert to preemptive
                for (int i = 0; i < readyque.size(); i++) { // if the current process is have high priority or not ??
                    if (readyque.get(i).agFactor < prio) {
                        prio = readyque.get(i).agFactor;
                        pos = i;
                    }
                }
                if (current == pos) {
                    if (readyque.get(current).quantum > 0 && readyque.get(current).BurstTime > readyque.get(current).quantum) {
                        readyque.get(current).BurstTime--;
                        readyque.get(current).quantum--;
                        OriginalTime++;
                    } else if (readyque.get(current).quantum == 0) { // Scenario 1
                        readyque.get(current).quantum = Math.ceil(0.1 * mean_of_readyqu()) + tempq;
                        Process pro = readyque.get(current);
                        readyque.remove(current);
                        readyque.add(pro);
                        prio = 10000;
                        val = true;
                    } else {// Scenario 3
                        OriginalTime += readyque.get(current).BurstTime;
                        processes.get(readyque.get(current).ProcessID).CompletionTime = OriginalTime;
                        processes.get(readyque.get(current).ProcessID).TurnAroundTime = OriginalTime - readyque.get(current).ArrivalTime;
                        processes.get(readyque.get(current).ProcessID).WaitingTime = processes.get(readyque.get(current).ProcessID).TurnAroundTime - processes.get(readyque.get(current).ProcessID).BurstTime;
                        readyque.remove(current);
                        TotalNumberOfProcesses++;
                        val = true;
                    }
                } else { // Scenario 2
                    readyque.get(current).quantum += tempq;
                    Process pro = readyque.get(current);
                    readyque.set(current, readyque.get(pos));
                    readyque.remove(pos);
                    readyque.add(pro);
                    val = true;
                    pos = 0;
                }
            }
        }
        System.out.print("\n\nProcess Execution order : ");
        for (int i = 0; i < procname.size(); i++) {
            System.out.print(timeofproc.get(i) + " -- " + procname.get(i) + " -- ");
        }
        System.out.print(OriginalTime + "\n\n");

        double AverageWaitingTime = 0.0, AverageTurnAroundTime = 0.0;
        System.out.println("Process Name \t\t Completion Time \t\t  Turnaround Time \t\t  Waititng Time");
        for (Process proc : processes) {
            System.out.println(proc.name + "\t\t\t\t" + proc.CompletionTime + "\t\t\t\t" + proc.TurnAroundTime + "\t\t\t\t" + proc.WaitingTime);
            AverageTurnAroundTime += proc.TurnAroundTime;
            AverageWaitingTime += proc.WaitingTime;
        }
        System.out.println("\nAverage Watiting time : " + (AverageWaitingTime / numOfProcesses));
        System.out.println("Average TurnAround time : " + (AverageTurnAroundTime / numOfProcesses));
    }
    
    static int largest(int[] arr) 
    { 
        int i; 
          
        // Initialize maximum element 
        int max = arr[0]; 
       
        // Traverse array elements from second and 
        // compare every element with current max   
        for (i = 1; i < arr.length; i++) 
            if (arr[i] > max) 
                max = arr[i]; 
       
        return max; 
    } 
    
    public void calcSJF() {
    	//TAT: CT-AT
        // WT: TAT - BT
        
        for(int i = 0; i < Process.NumberOfProcesses; i++) {
       	 turnAroundTime.add(end[i] - Process.arrivalTime.get(i));
        }
        for(int t = 0; t < Process.NumberOfProcesses; t++) {
       	 waitingTime.add(turnAroundTime.get(t) - Process.burstTime.get(t));
        }
        for(int j = 0; j < start.length; j++) {
       	 System.out.println("start : "+ start[j] + "\nEnd : " + end[j] + "\n");
        }
        for(int k = 0; k < resNameV.size(); k++) {
       	 System.out.println("name : "+ resNameV.elementAt(k) + "\n");
        }
        System.out.println("----------------------------------------------");
        for(int k = 0; k < completionTime.size(); k++) {
       	 System.out.println("CT : "+ completionTime.get(k) + "\n");
        }
        System.out.println("----------------------------------------------");
        for(int k = 0; k < completionTime.size(); k++) {
       	 System.out.println("TAT : "+ turnAroundTime.get(k) + "\n");
        }
        System.out.println("----------------------------------------------");
        for(int k = 0; k < completionTime.size(); k++) {
       	 System.out.println("WT : "+ waitingTime.get(k) + "\n");
       	 
        }
    }
    public void calcPriority() {
    	 for(int j = 0; j < Process.NumberOfProcesses; j++) {
        	 System.out.println("startV : "+ startV.elementAt(j) + "\nEndV : " + endV.elementAt(j) + "\n");
         }
         for(int k = 0; k < Process.NumberOfProcesses; k++) {
        	 System.out.println("name : "+ resNameV.elementAt(k) + "\n");
         }
    }
    
}
