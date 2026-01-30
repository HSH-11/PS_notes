import java.util.*;

// 멈춰둔 과제가 여러 개일 경우, 가장 최근에 멈춘 과제부터 시작해야하므로 이를 스택 처리

class Solution {
    
    class Task {
        String name;
        int start;
        int remaining;
        
        public Task (String name, int start, int remaining) {
            this.name = name;
            this.start = start;
            this.remaining = remaining;
        }
    }
      
    public String[] solution(String[][] plans) {
        List<Task> tasks = new ArrayList<>();
        for (String[] plan : plans) {
            String[] time = plan[1].split(":");
            int start = Integer.parseInt(time[0]) * 60 + Integer.parseInt(time[1]);
            int remaining = Integer.parseInt(plan[2]);
            tasks.add(new Task(plan[0], start, remaining));
        }
        
        // 시작 시간 순으로 정렬
        Collections.sort(tasks, (a,b) -> a.start - b.start);
        
        Stack<Task> stopStack = new Stack<>();
        List<String> result = new ArrayList<>();
        
        for (int i = 0; i < tasks.size() - 1; i++) {
            Task current = tasks.get(i);
            Task next = tasks.get(i + 1);
            
            int nowToEnd = current.start + current.remaining;
            
            // 현재 과제를 끝내도 다음 과제 시작까지 시간이 남음
            if (nowToEnd <= next.start) {
                result.add(current.name);
                int idleTime = next.start - nowToEnd;
                
                // 남은 시간 동안 스택에 있는 과제 해결
                while (!stopStack.isEmpty() && idleTime > 0) {
                    Task stopped = stopStack.peek();
                    if (stopped.remaining <= idleTime) {
                        idleTime -= stopped.remaining;
                        result.add(stopStack.pop().name);
                    } else {
                        stopped.remaining -= idleTime;
                        idleTime = 0;
                    }
                }
            }
            
            // 현재 과제를 끝내지 못하고 다음 과제 시작 (스택에 저장)
            else {
                current.remaining -= (next.start - current.start);
                stopStack.push(current);
            }        
        }
        
        // 마지막 과제 처리
        result.add(tasks.get(tasks.size() - 1).name);
        
        // 스택에 남은 과제 처리
        while (!stopStack.isEmpty()) {
            result.add(stopStack.pop().name);
        }
        
        
        return result.toArray(new String[0]);
    }
    
    
    
    
}