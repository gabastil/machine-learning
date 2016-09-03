import sys
sys.path.append("../..")
from Document.DocumentPlus import DocumentPlus
from nltk.stem.porter import PorterStemmer
from nltk.stem import WordNetLemmatizer

class DirichletDocument(DocumentPlus):

	def __init__(self, filePath=None, savePath=None, stop_puncs="./data/stoppuncs.txt", stop_words="./data/stopwords.txt", norm_words="./data/normwords.txt", lc=True):
		""" constructor for this class with two parameters
			@param	filePath: input file
			@param	savePath: output location
		"""

		super(DirichletDocument, self).__init__(filePath=filePath, savePath=filePath, stop_puncs=stop_puncs, stop_words=stop_words, norm_words=norm_words)
		super(DirichletDocument, self).removeOwnPunctuation()
		super(DirichletDocument, self).removeOwnContractions()
		super(DirichletDocument, self).removeOwnStopWords(sort=False, lc=lc)

		self.textFile = self.stem(self.textFile)
		self.frequency = self.getFrequency(self.textFile)

	def stem(self, text, lemmatizer=None):
		""" stem words in this object
			@param	text: list of words to stem
			@param	lemmatizer: specific lemmatizer to use
			@return	list of stemmed words
		"""
		if lemmatizer is None:
			lemmatizer = WordNetLemmatizer()

		lemmatize = lemmatizer.lemmatize

		return [lemmatize(word, 'v') for word in self.textFile]

	def getFrequency(self, text):
		"""	get a frequency count of input text
			@param	text: list of words to count
			@return	dictionary of words and frequencies
		"""
		frequency = dict()

		textSize = len(text)*1.

		for word in text:
			if word not in frequency:
				frequency[word] = 1
			else:
				frequency[word] += 1

		for word in frequency.keys():
			frequency[word] = frequency[word]/textSize
		
		return frequency

if __name__=="__main__":
	dd = DirichletDocument(filePath="./data/test.txt", lc=False)
	
	dd.toString()
