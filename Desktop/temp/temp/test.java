public boolean isPalindrome(int x) {
        if (x<0){
            return false;
        } else {
        String convert = Integer.toString(x);
        Stack<char> myStack = new Stack<>();
        for (int i=0;i<convert.length();i++){
            myStack.push(convert.charAt(i));
        }
        for (int i=0;i<convert.length();i++){
            if (myStack.peek()==convert.charAt(i)){
                myStack.pop();
            }
            else return false;            
        }
        
        }
        return true;
        
    }