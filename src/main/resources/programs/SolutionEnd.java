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
        System.out.println("--------");
        Class[] inputClasses = yourMethod.getParameterTypes();
        Type[] inputTypes = yourMethod.getGenericParameterTypes();

        Object[] inputList = new Object[args.length];
        for(int i = 0; i < args.length;i++){
            inputList[i] = PaserParam(inputTypes[i],inputClasses[i],args[i]);
        }

        Object output = yourMethod.invoke(solution,inputList);
        return output;
    }
     private static Object PaserParam(Type type,Class c, String arg){
         switch (c.getTypeName()){
             case "int":
             case "java.lang.Integer":
                 return Integer.parseInt(arg);
             case "long":
             case "java.lang.Long":
                 return Long.parseLong(arg);
             case "java.lang.String":
                 return arg;
             case "java.util.List":
                 ParameterizedType p = (ParameterizedType)type;
                 Type templateType= p.getActualTypeArguments()[0];
                 return parserList(templateType,arg);
             default:
                 //System.out.println(type);
                 return arg;
         }
     }
     private static Object parserList(Type templateType, String arg){
        switch (templateType.getTypeName()){
            case "java.lang.Integer":
                arg = arg.replace("[","");
                arg = arg.replace("]","");
                String[] args = arg.split(",");
                List<Integer> result = new ArrayList<>();
                for(int i = 0;i < args.length;i++){
                    result.add(Integer.parseInt(args[i]));
                }
                return result;
            default:
                return arg;
        }

     }
}


