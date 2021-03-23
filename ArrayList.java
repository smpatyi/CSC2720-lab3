class ArrayList {
   public int maxNumStages=27 ;
   public Object[] stage=new Object[maxNumStages] ;
   public int numStages=0 ;
   public int sizeLastStage=0 ;
   public int stageSize[]={8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 1048576, 2097152, 4194304, 8388608, 16777216, 33554432, 67108864, 134217728, 268435456, 536870912} ;
   public void initializeStage(int i){
      stage[i]=new Object[stageSize[i]] ;
   }
   
   public void clear() {
      for(int i=0 ; i<stage.length ; i++){
         stage[i]=null ;
      }
      numStages=0 ;
      sizeLastStage=0 ;
   }
   
   public boolean contains(Object e) {
      //System.out.println(".contains("+e+")") ;
      for(int i=0 ; i<numStages ; i++){
	 //System.out.println(".contains,i="+i) ;
         if(stage[i]==null){
            return false ;
         }
         for(int j=0 ; (i<(numStages-1) && j<stageSize[i]) || 
			 (i==(numStages-1) && j<sizeLastStage) ; j++) {
	    //System.out.println(".contains,j="+j) ;
            //System.out.println(".contains,"+((Object[]) stage[i])[j]) ;
	    Object f=((Object[]) stage[i])[j] ;
            if(f==e){
               return true ;
            }
	    if((e!=null) && e.equals(f)){
	       return true ;
	    }
         }
      }
      return false ;
   }
   
   public boolean isEmpty() {
      return numStages<1 ;
   }

   public Object get(int i) {
      System.out.println("get, numStages="+numStages) ;
      int cumSize=0 ;
      for(int j=0 ; j<numStages ; j++) {
         if(i<cumSize+stageSize[j]) {
	    if(j==numStages-1 && i>=cumSize+sizeLastStage) {
	       break ;
	    }
            return ((Object[]) stage[j])[i-cumSize] ;
         }
         cumSize+=stageSize[j] ;
      }
      //return null ;
      return "ArrayList.get says that element of index "+i+" does not exist." ;
   }

   public int size() {
      int s=0 ;
      for(int i=0 ; i<(numStages-1) ; i++) {
         s+=stageSize[i] ;
      }
      return s+sizeLastStage ;
   }

   public void addWisdom() {
      initializeStage(0) ;
      ((Object[]) stage[0])[0]="wisdom" ;
      numStages=1 ;
      sizeLastStage=1 ;
   }
   
   public void addWisdomAnd42() {
      addWisdom() ;
      ((Object[]) stage[0])[1]=42 ;
      numStages=1 ;
      sizeLastStage=2 ;
   }
   
   public void addWisdom42AndNull() {
      addWisdomAnd42() ;
      ((Object[]) stage[0])[2]=null ;
      numStages=1 ;
      sizeLastStage=3 ;
   }

   public String toString() {
      String s="ArrayList[" ;
      for(int i=0 ; i<numStages ; i++) {
	 s += "(stage "+i+")" ;
	 //System.out.println("toString,"+s) ;
	 for(int j=0 ; (i<(numStages-1) && j<stageSize[i]) ||
	       (i==(numStages-1) && j<sizeLastStage) ; j++) {
	    if(j>0) {
	       s += "," ;
	    }
	    //System.out.println("toString,"+s) ;
	    Object e=((Object[]) stage[i])[j] ;
	    if(e==null){
	       s += "null" ;
	    } else {
	       s += ((Object[]) stage[i])[j].toString() ;
	    }
	 }
      }
      s += "]" ;
      return s ;
   }

   public void add(Object e) {
      if(numStages==0) {
	 initializeStage(0) ; 
	 numStages=1 ;
	 ((Object[]) stage[0])[0]=e ;
	 sizeLastStage=1 ;
	 return ;
      }
      if(sizeLastStage==stageSize[numStages-1]) {
	 initializeStage(numStages) ;
	 ((Object[]) stage[numStages])[0]=e ;
	 numStages++ ;
	 sizeLastStage=1 ;
	 return ;
      }
      ((Object[]) stage[numStages-1])[sizeLastStage]=e ;
      sizeLastStage++ ;
   }

   public void ensureCapacity(int minCapacity) {
      return ;
   }

   public void remove(int i) {
      int cumSize=0 ;
      int j ;
      for(j=0 ; j<numStages ; j++) {
	 if(i>=cumSize+stageSize[j]) {
	    cumSize+=stageSize[j] ;
	    continue ;
	 }
	 break ;
      }
      System.out.println("remove, before first if, i="+i+",j="+j+",numStages-1="+(numStages-1)+ ",cumSize="+cumSize+",sizeLastStage="+sizeLastStage) ;
      if(j>=numStages) {
	 return ;
      }
      if(j==numStages-1 && i>=cumSize+sizeLastStage) {
	 System.out.println("remove, return 1,i="+i) ;
	 return ;
      }
      System.out.println("j="+j+",i="+i+",cumSize="+cumSize) ;
      for(int J=j ; J<numStages ; J++) {
	 if(j==numStages-1 && sizeLastStage==1) {
	    if(i==cumSize) {
	       stage[j]=null ;
	       sizeLastStage=stageSize[j-1] ;
	       numStages-- ;
	       System.out.println("remove, return 2,i="+i) ;
	       return ;
	    }
	 }
	 int k0=0 ;
	 if(J==j) {
	    k0=i-cumSize ;
	 }
	 for(int k=k0 ; k<stageSize[J]-1 ; k++) {
	    ((Object[]) stage[J])[k]=((Object[]) stage[J])[k+1] ;
	 }
	 if(J<numStages-1) {
	    ((Object[]) stage[J])[stageSize[J]-1]=((Object[]) stage[J+1])[0] ;
	 }
	 
      }
      ((Object[]) stage[numStages-1])[sizeLastStage-1]=null ;
      sizeLastStage-- ;
      //numStages-- ;
      System.out.println("remove, return 3,i="+i) ;
   }

   public void add(int index, Object e) {
      if(index<0 || index>size()) {
	 throw new IndexOutOfBoundsException("Index "+index+" too low or too high in ArrayList.add(index, element).");
	 //return ;	// Throw error here "IndexOutOfBoundsException".
      }
      if(isEmpty()) {
	 add(e) ;
	 return ;
      }
      int stageNumIndex=0 ;
      int cumSize=0 ;
      for(int i=0 ; i<numStages ; i++) {
	 if(index<cumSize+stageSize[i]) {
	    stageNumIndex=i ;
	    break ;
	 }
      }
      System.out.println("index="+index+",stageNumIndex="+stageNumIndex) ;
      if(sizeLastStage==stageSize[numStages-1]) {
	 initializeStage(numStages) ;
	 ((Object[]) stage[numStages])[0]=((Object[]) stage[numStages-1])[sizeLastStage-1] ;
      }

      if(numStages==1 && index<stageSize[1]) {
	 if(sizeLastStage<stageSize[0]) {
	    for(int i=sizeLastStage-1 ; i>=index ; i--) {
	       ((Object[]) stage[0])[i+1]=((Object[]) stage[0])[i] ;
	    }
	    ((Object[]) stage[0])[index]=e ;
	    sizeLastStage++ ;
	    return ;
	 }
	 initializeStage(1) ;
	 ((Object[]) stage[1])[0]=((Object[]) stage[0])[stageSize[0]-1] ;
	 for(int i=sizeLastStage-2 ; i>=index ; i--) {
	    ((Object[]) stage[0])[i+1]=((Object[]) stage[0])[i] ;
	 }
	 ((Object[]) stage[0])[index]=e ;
	 numStages=2 ;
	 sizeLastStage=1 ;
	 return ;
      }
   }

   public static void main(String[] args){
      /*
      ArrayList a=new ArrayList() ;
      System.out.println("a.get(0),"+a.get(0)) ;
      System.out.println("a.isEmpty(),"+a.isEmpty()) ;
      System.out.println("a.size(),"+a.size()) ;
      System.out.println("a.contains(\"wisdom\"),"+a.contains("wisdom")) ;
      a.addWisdom() ;
      System.out.println("a.contains(\"wisdom\"),"+a.contains("wisdom")) ;
      System.out.println("a.get(0),"+a.get(0)) ;
      System.out.println("a.isEmpty(),"+a.isEmpty()) ;
      System.out.println("a.size(),"+a.size()) ;  
      a.addWisdomAnd42() ;
      System.out.println("a.contains(42),"+a.contains(42)) ;
      System.out.println("a.get(1),"+a.get(1)) ;
      System.out.println("a.isEmpty(),"+a.isEmpty()) ;
      System.out.println("a.size(),"+a.size()) ;  
      a.addWisdom42AndNull() ;
      System.out.println("a.contains(null),"+a.contains(null)) ;
      System.out.println("a.get(2),"+a.get(2)) ;
      System.out.println("a.isEmpty(),"+a.isEmpty()) ;
      System.out.println("a.size(),"+a.size()) ;                
      System.out.println("a.toString(),"+a.toString()) ;
      ArrayList b=new ArrayList() ;
      for(int i=0 ; i<200 ; i++) {
	 b.add(i) ;
      }
      System.out.println("b.toString(),"+b.toString()) ;
      System.out.println("b.contains(4),"+b.contains(4)) ;
      System.out.println("b.contains(14),"+b.contains(14)) ;
      System.out.println("b.contains(24),"+b.contains(24)) ;
      System.out.println("b.contains(34),"+b.contains(34)) ;
      System.out.println("b.contains(134),"+b.contains(134)) ;
      System.out.println("b.size(),"+b.size()) ;  
      System.out.println("b.get(4),"+b.get(4)) ;
      System.out.println("b.get(134),"+b.get(134)) ;
      for(int i=0 ; i<200 ; i++) {
	 if(! b.contains(i)) {
	    System.out.println("b does not contain "+i) ;
	 }
      }
      */
      ArrayList c=new ArrayList() ;
      for(int i=0 ; i<8 ; i++) {
	 c.add(i,""+i) ;
      }
      System.out.println("c.toString(),"+c.toString()) ;
      System.out.println("add Zero") ;
      c.add(0,"Zero") ;
      System.out.println("c.toString(),"+c.toString()) ;
      System.out.println("add Four") ;
      c.add(4,"Four") ;
      System.out.println("c.toString(),"+c.toString()) ;
      System.out.println("add 8") ;
      c.add(8,"8") ;
      System.out.println("c.toString(),"+c.toString()) ;
      //c.add(18,"18") ;
      /*
      System.out.println("c.toString(),"+c.toString()) ;
      System.out.println("c.contains(4),"+c.contains(4)) ;
      System.out.println("c.contains(14),"+c.contains(14)) ;
      System.out.println("c.contains(24),"+c.contains(24)) ;
      System.out.println("c.contains(34),"+c.contains(34)) ;
      System.out.println("c.contains(134),"+c.contains(134)) ;
      System.out.println("c.size(),"+c.size()) ;  
      System.out.println("c.get(4),"+c.get(4)) ;
      System.out.println("c.get(134),"+c.get(134)) ;
      System.out.println("c.contains(\"4\"),"+c.contains("4")) ;
      System.out.println("c.contains(\"14\"),"+c.contains("14")) ;
      System.out.println("c.contains(\"24\"),"+c.contains("24")) ;
      System.out.println("c.contains(\"34\"),"+c.contains("34")) ;
      System.out.println("c.contains(\"134\"),"+c.contains("134")) ;
      System.out.println("c.isEmpty(),"+c.isEmpty()) ;
      System.out.println("c.size(),"+c.size()) ;  
      System.out.println("c.contains(\"120\"),"+c.contains("120")) ;
      System.out.println("c.contains(\"121\"),"+c.contains("121")) ;
      System.out.println("c.get(121),"+c.get(121)) ;
      //System.out.println("c.toString(),"+c.toString()) ;
      //c.remove(200) ;
      System.out.println("c.toString(),"+c.toString()) ;
      System.out.println("c.remove(120)") ;
      c.remove(120) ;
      System.out.println("c.size(),"+c.size()) ;  
      System.out.println("c.contains(\"120\"),"+c.contains("120")) ;
      System.out.println("c.toString(),"+c.toString()) ;
      System.out.println("c.get(119),"+c.get(119)) ;
      c.remove(119) ;
      System.out.println("c.size(),"+c.size()) ;  
      System.out.println("c.contains(\"119\"),"+c.contains("119")) ;
      System.out.println("c.toString(),"+c.toString()) ;
      System.out.println("c.contains(\"50\"),"+c.contains("50")) ;
      c.remove(50) ;
      System.out.println("c.size(),"+c.size()) ;  
      System.out.println("c.contains(\"50\"),"+c.contains("50")) ;
      System.out.println("c.toString(),"+c.toString()) ;
      System.out.println("c.contains(\"8\"),"+c.contains("8")) ;
      c.remove(8) ;
      System.out.println("c.size(),"+c.size()) ;  
      System.out.println("c.contains(\"8\"),"+c.contains("8")) ;
      System.out.println("c.toString(),"+c.toString()) ;
      for(int i=4 ; i<60 ; i=i+4) {
	 System.out.println("c.get("+i+"),"+c.get(i)) ;
	 c.remove(i) ;
	 System.out.println("c.toString(),"+c.toString()) ;
      }
      for(int i=4 ; i<100 ; i++) {
	 System.out.println("c.get(56),"+c.get(56)) ;
	 c.remove(56) ;
	 System.out.println("c.toString(),"+c.toString()) ;
      }
      System.out.println("c.size(),"+c.size()) ;  
      */
      /*
      c.clear() ;
      System.out.println("c.isEmpty(),"+c.isEmpty()) ;
      System.out.println("c.size(),"+c.size()) ;  
      ArrayList d=new ArrayList() ;
      for(int i=0 ; i<64 ; i++) {
	 d.add("d"+i) ;
      }
      System.out.println("d.toString(),"+d.toString()) ;
      for(int i=0 ; i<10 ; i++) {
	 d.remove(2*i) ;
	 System.out.println("i="+i+" d.toString(),"+d.toString()) ;
      }
      */
   }   
}
