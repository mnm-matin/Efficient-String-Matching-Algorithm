package matcher;

public class StudentClass {

	public KMPMatcher kmpMatcher;

	public StudentClass(String text, String pattern) {
		kmpMatcher = new KMPMatcher(text, pattern);
	}

	public void buildPrefixFunction() {
		kmpMatcher.setPrefixFunction(computePrefixFunction(kmpMatcher.getPattern()));
	}
	
	public static int[] computePrefixFunction(String pattern) {
		//this function returns a pi array called "pei"
		//pattern is matched against the pattern
		int k;
		int m=pattern.length();
		int pei[];
		pei=new int[m];						
		pei[0]=0;					//general case
		k=0;
									
		for (int q=2; q<=m;q++) {		//-1 due to charAt starting from 0
			while (k>0 && pattern.charAt(k+1-1)!=pattern.charAt(q-1)) {
				k=pei[k-1];		//starting arrays from 0 so -1 while using arrays
			}
			if (pattern.charAt(k+1-1)==pattern.charAt(q-1)) {
					k=k+1;
					}
				pei[q-1]=k;			//stores max value of k
			}
		return pei;	
	}


	public static class KMPMatcher {

		private String text;
		private String pattern;
		private int textLen;
		private int patternLen;
		private int[] prefixFunction;
		private Queue matchIndices;

		public KMPMatcher(String text, String pattern) {
			this.text = text;
			this.pattern = pattern;
			this.textLen = text.length();
			this.patternLen = pattern.length();
			this.prefixFunction = new int[patternLen + 1];
			this.matchIndices = new Queue();
		}

		public void setPrefixFunction(int[] prefixFunction) {
			this.prefixFunction = prefixFunction;
		}

		public int[] getPrefixFunction() {
			return prefixFunction;
		}

		public String getPattern() {
			return pattern;
		}

		public Queue getMatchIndices() {
			return matchIndices;
		}

		public void search() {
			//the pattern is used with the pei array from computePrefixFunction()
			//pattern is compared against the text
			//queue can be returned using getMatchIndices()
			int n=this.textLen;		
			int m=this.patternLen;
			int pei[]= computePrefixFunction(this.pattern);
			int q=0;
			
			for(int i=1;i<=n;i++) {			//-1 due to charAt starting from 0
				while(q>0 && this.pattern.charAt(q+1-1)!=this.text.charAt(i-1)) {
					q=pei[q-1];
				}
				if (this.pattern.charAt(q+1-1)==this.text.charAt(i-1)) {
					q=q+1;
				}
				if (q==m) {
					this.matchIndices.enqueue(i-m);		//enqueue adds to right
					q=pei[q-1];
				}
			}
		}
	}
}