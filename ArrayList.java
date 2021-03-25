class ArrayList {
   public int maxNumStages=27 ;
   public Object[] stage=new Object[maxNumStages] ;
   public int numStages=0 ;
   public int sizeLastStage=0 ;
   public int stageSize[]={8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 1048576, 2097152, 4194304, 8388608, 16777216, 33554432, 67108864, 134217728, 268435456, 536870912} ;

   public int[] i2sj(int i) {
      int[] sj=new int[2] ;
      int cumSize=0 ;
      for(int j=0 ; j<maxNumStages ; j++) {
	 if(i<cumSize+stageSize[j]){
	    sj[0]=j ;
	    sj[1]=i-cumSize ;
	    return sj ;
	 }
	 cumSize += stageSize[j] ;
      }
      return sj ;
   }

   public void add(int index, Object e) {
      int s=size() ;
      if(index<0 || index>s) {
	 throw new IndexOutOfBoundsException("Index "+index+" too low or too high in ArrayList.add(index, element).");
      }
      if(index==s) {
	 add(e) ;
	 return ;
      }
      for(int j=s-1 ; j>=index ; j--) {
	 int[] sj1=i2sj(j+1) ;
	 int[] sj=i2sj(j) ;
	 if(sj1[0]>=numStages){
	    initializeStage(sj1[0]) ;
	 }
	 ((Object[]) stage[sj1[0]])[sj1[1]]=((Object[]) stage[sj[0]])[sj[1]] ;
      }
      int[] sj=i2sj(index) ;
      ((Object[]) stage[sj[0]])[sj[1]]=e ;
      int[] sj2=i2sj(s) ;
      numStages=sj2[0]+1 ;
      sizeLastStage=sj2[1]+1 ;
   }

   public void remove(int index) {
      int s=size() ;
      if(index<0 || index>=s) {
	 throw new IndexOutOfBoundsException("Index "+index+" too low or too high in ArrayList.add(index, element).");
      }
      if(s==1) {
	 clear() ;
	 return ;
      }
      for(int j=index ; j<=s-2 ; j++) {
	 int[] sj=i2sj(j) ;
	 int[] sj1=i2sj(j+1) ;
	 ((Object[]) stage[sj[0]])[sj[1]]=((Object[]) stage[sj1[0]])[sj1[1]] ;
      }
      int sj2[]=i2sj(s-1) ;
      ((Object[]) stage[sj2[0]])[sj2[1]]=null ;
      int sj3[]=i2sj(s-2) ;
      if(sj3[0]+1<numStages) {
	 stage[sj2[0]]=null ;
      }
      numStages=sj3[0]+1 ;
      sizeLastStage=sj3[1]+1 ;
   }

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
      for(int i=0 ; i<numStages ; i++){
         if(stage[i]==null){
            return false ;
         }
         for(int j=0 ; (i<(numStages-1) && j<stageSize[i]) || 
			 (i==(numStages-1) && j<sizeLastStage) ; j++) {
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

   public String toString() {
      String s="ArrayList[" ;
      for(int i=0 ; i<numStages ; i++) {
	 s += "(stage "+i+")" ;
	 for(int j=0 ; (i<(numStages-1) && j<stageSize[i]) ||
	       (i==(numStages-1) && j<sizeLastStage) ; j++) {
	    if(j>0) {
	       s += "," ;
	    }
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

   public static void main(String[] args){
      ArrayList a=new ArrayList() ;
      for(int i=0 ; i<100 ; i++) {
	 a.add(i) ;
      }
      System.out.println("a.toString(),"+a.toString()) ;
      for(int i=0 ; i<100 ; i++) {
	 int[] sj=a.i2sj(i) ;
	 System.out.println("i2sj("+i+")="+sj[0]+","+sj[1]) ;
      }
      for(int i=0 ; i<20 ; i++) {
	 a.add(i,"a"+i) ;
      }
      System.out.println("a.toString(),"+a.toString()) ;
      for(int i=0 ; i<20 ; i++) {
	 a.add(30+2*i,"b"+i) ;
      }
      System.out.println("a.toString(),"+a.toString()) ;
      for(int i=0 ; i<20 ; i++) {
	 a.remove(30+2*i) ;
      }
      System.out.println("a.toString(),"+a.toString()) ;
      int s=a.size() ;
      for(int i=0 ; i<s ; i++) {
	 a.remove(0) ;
	 System.out.println("a.remove(0),a.size()="+a.size()) ;
	 System.out.println("a.toString(),"+a.toString()) ;
      }
   }   
}
