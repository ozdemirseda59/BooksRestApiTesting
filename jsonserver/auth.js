
module.exports = (req, res, next) => {
if(req.method==='POST'){
if(!req.body.title){
      return res.status(500).send({ error: "Field 'title' is required"});
  }
  if(!req.body.authar){
      return res.status(500).send({ error: "Field 'authar' is required"});
  }
  if(req.body.id){
       return res.status(500).send({error:"Field 'id' is read only"});
  }
}
  next();
};

