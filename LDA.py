
import os, re, random
from nltk.corpus import stopwords as nltk_stopwords
from nltk.stem.porter import PorterStemmer
import math
from collections import defaultdict

class LDA(object):

	def __init__(self, topics=3, docs="data/docs", stopwords="data/stopwords.txt"):
		self.topics = topics-1

		# Get a full list of stopwords
		with open(stopwords, 'r') as stopwords:
			self.stopwords = stopwords.read().split()
			self.stopwords = set(self.stopwords).union(set(nltk_stopwords.words('english')))

		# Build vocabulary set
		self.documents  = list()
		self.vocabulary = set()

		union = self.vocabulary.union
		for doc in os.listdir(docs):

			with open("{}/{}".format(docs, doc), 'r') as document:
				doc = [re.sub(r"\W+","",w) for w in document.read().lower().split() if w not in self.stopwords and len(w) < 20]
				doc = [PorterStemmer().stem(w) for w in doc]
				self.vocabulary = self.vocabulary.union(set(doc))

			if len(doc) > 0:
				self.documents.append(doc)

		self.documents_num 	= list()
		self.vocabulary = list(self.vocabulary)

		# Replace words in docs with associated vocabulary index
		for doc in self.documents:

			for i, pv in enumerate(self.vocabulary):
				doc = [i if pv==w else w for w in doc]

			self.documents_num.append(doc)


		# ASSIGN VOCABULARY TO TOPICS
		self.topic_distribution = defaultdict(list)

		for doc in self.documents_num:

			for w in doc:
				random_topic_assignment = int(round(random.random()*topics))
				self.topic_distribution[random_topic_assignment].append(w)

		# CALCULATE TOPIC PROBABILITIES IN DOCUMENTS
		for topic_number, topic_list in self.topic_distribution.items():
			topic_set = set(topic_list)
			#print topic_number, len(topic_list), len(topic_set)
			for t in topic_list:
				print topic_number, self.tfidf(t)

			#for document in self.documents_num:

			#	print topic_number, [(t,self.tfidf(t)) for t in topic_set]

			#topic_set = set(topic)
			#print topic_set


	def term_frequency(self, word):
		try:
			w = word
			if type(word)==type(str):
				w = self.vocabulary.index(word)
			return [doc.count(w)/(len(doc)*1.) for doc in self.documents_num]
		except(ValueError):
			return [0 for doc in self.documents_num]

	def document_frequency(self, word):
		try:
			w = word
			if type(word)==type(str):
				w = self.vocabulary.index(word)
			counts = [1 if w in doc else 0 for doc in self.documents_num]
			return math.log((1.*len(counts))/sum(counts))
		except(ValueError, ZeroDivisionError):
			return 0

	def tfidf(self, word):
		tf = self.term_frequency(word)
		df = self.document_frequency(word)
		#print word, df, tf
		return [t*df for t in tf]

		#print os.listdir(docs)
		#print len(self.documents_num), self.documents_num
		#print self.documents
		#print vocabulary

	"""
	def split_topics(self):
		topic_list = [list() for topic in xrange(self.topics)]

		for doc in self.documents:
			for w in doc:
				random_index = int(round(random.random()*(self.topics-1)))
				topic_list[random_index].append(w.lower())

		#print len(topic_list), len(topic_list[0]), len(topic_list[1]), len(topic_list[2])
		return topic_list

	def term_frequency(self, word):
		return [doc.count(word)/self.doc_lens[i] for i,doc in enumerate(self.documents)]

	def inverse_document_frequency(self, word):
		occurrence = sum(1. if word in doc else 0. for doc in self.documents)
		return math.log((self.documents_size/occurrence))

	def tf(self):

		frequencies = list()

		for doc in self.documents:

			doc_n = len(doc)*1.
			doc_tf = dict()

			for w in set(doc):
				doc_tf[w] = doc.count(w)/doc_n
			frequencies.append(doc_tf)

		return frequencies

	def idf(self):
		import math
		topics = self.split_topics()
		docs_n = len(self.documents)*1.
		terms = set((term for topic in topics for term in topic))

		frequencies = dict()

		for term in list(terms)[:20]:
			doc_freq = sum((1 for doc in self.documents if term in doc))
			frequencies[term] = math.log(docs_n/doc_freq)

		return frequencies

	def tfidf(self):
		tf = self.tf(); idf=self.idf()
		print len(tf), len(idf), len(tf[0]), idf

	def freq(self):
		pass
	"""

if __name__ == '__main__':
	l = LDA()
	#print l.term_frequency("read"), l.term_frequency("eat"), l.tfidf("like")
	#print l.document_frequency("eat")
	print l.tfidf("read"); print l.tfidf("eat"); print l.tfidf("like")
	#l.split_topics(); 
	#l.tfidf()
	#tf = l.term_frequency("mga"); idf = l.inverse_document_frequency("mga")
	#print [t/idf for t in tf]
