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
		
		int k;
		int m=pattern.length();
		int pei[];
		pei=new int[m];
		pei[0]=0;	//general case
		k=0;
									
		for (int q=2; q<=m;q++) {
			while (k>0 && pattern.charAt(k+1-1)!=pattern.charAt(q-1)) {
				k=pei[k-1];
			}
			if (pattern.charAt(k+1-1)==pattern.charAt(q-1)) {
					k=k+1;
					}
				pei[q-1]=k;
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
			int n=this.textLen;
			int m=this.patternLen;
			int pei[]= computePrefixFunction(this.pattern);
			int q=0;
			
			for(int i=1;i<=n;i++) {
				while(q>0 && this.pattern.charAt(q+1-1)!=this.text.charAt(i-1)) {
					q=pei[q-1];
				}
				if (this.pattern.charAt(q+1-1)==this.text.charAt(i-1)) {
					q=q+1;
				}
				if (q==m) {
					this.matchIndices.enqueue(i-m);
					q=pei[q-1];
				}
			}
		}
	}
}