
var forge = require('node-forge');

function generateCSR(publicKey, privateKey, commonName, countryName, organizationName, organization)
{
 
var csr = forge.pki.createCertificationRequest();
csr.publicKey = publicKey;
csr.setSubject([{
  name: 'commonName',
  value: commonName
}, {
  name: 'countryName',
  value: countryName
}, {
  name: 'organizationName',
  value: organizationName
}, {
  shortName: 'OU',
  value: organization
}]);

csr.sign(privateKey);

return csr;
}

function generateKeyPair(type) {

  var keys = forge.pki.rsa.generateKeyPair(1024);
  var fs = require('fs');
  
  fs.writeFile("publicKey"+type+".txt",  keys.publicKey , function (err) {
  if (err) return console.log(err);
  });


  fs.writeFile("privateKey"+type+".txt",  keys.privateKey , function (err) {
  if (err) return console.log(err);
  });
  
  return keys;
}

function convertCsrToPem(csr)
{
 return forge.pki.certificationRequestToPem(csr);
}

module.exports.generateKeyPair = generateKeyPair;
module.exports.generateCSR = generateCSR;
module.exports.convertCsrToPem = convertCsrToPem;