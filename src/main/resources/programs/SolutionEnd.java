}

class Parser {
    public static Object parserParameter(Object solution,String[] args) throws InvocationTargetException, IllegalAccessException {
        Method[] declaredMethods = solution.getClass().getDeclaredMethods();//自己类中的私有和公有方法
        Method[] methods = solution.getClass().getMethods();//自己和继承类中的公有方法
        List<Method> declaredMethodList = new ArrayList<>(Arrays.asList(declaredMethods));
        List<Method> methodList = new ArrayList<>(Arrays.asList(methods));
        declaredMethodList.retainAll(methodList);
        if(declaredMethodList.size() != 2){
            throw new RuntimeException("只能有一个定义的public函数，其余函数请设置为private");
        }
        Method yourMethod = null;
        for(Method m : declaredMethodList){
            if(!m.getName().equals("main")){
                yourMethod = m;//获得不是main的public函数
            }
        }
        System.out.println("Call Funtion : "+yourMethod.getName());
        Class[] inputClasses = yourMethod.getParameterTypes();
        Object[] inputList = new Object[args.length];
        for(int i = 0; i < args.length;i++){
            switch (inputClasses[i].getName()){
                case "int":
                    inputList[i] = Integer.parseInt(args[i]);
                    break;
                case "java.lang.String":
                    inputList[i] = args[i];
                    break;
            }
        }
        Object output = yourMethod.invoke(solution,inputList);
        return output;
    }
}

