import sklearn
from sklearn import metrics
from sklearn.svm import SVC
def train_and_test(X_train,Y_train,X_test,Y_test):
    model = SVC()
    model.fit(X_train,Y_train)
    predicted = model.predict(X_train)
    expected = Y_test
    print(metrics.classification_report(expected, predicted))
    print(metrics.confusion_matrix(expected, predicted))
    return model
