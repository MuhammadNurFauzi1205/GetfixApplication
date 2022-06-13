const admin = require("firebase-admin");
const credentials = require("./serviceAccountKey.json");

admin.initializeApp({
	credential: admin.credential.cert(credentials)
	databaseURL: “https://getfix-351406-default-rtdb.asia-southeast1.firebasedatabase.app”
});

const getAuthToken = (req, res, next) => {
	if (
		req.headers.authorization &&
		req.headers.authorization.split('')[0] === 'Bearer')
		{
		  req.authToken=req.headers.authorization.split('')[1];
		} else{
			req.authToken = null;
		}
	next ();
};

const checkAuth = (req, res, next) => {
 getAuthToken(req, res, async () => {
    try {
      const { authToken } = req;
      const userInfo = await admin
        .auth()
        .verifyIdToken(authToken);
      req.authId = userInfo.uid;
      return next();
    } catch (e) {
      return res
        .status(401)
        .send({ error: 'Anda Belum Login' });
    }
  });
};