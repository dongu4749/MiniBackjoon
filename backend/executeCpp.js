const { exec } = require("child_process")
const { error } = require("console")
const fs = require('fs')
const path = require('path')
const { stdout } = require("process")

const outputPath = path.join(__dirname, "outputs")

if(!fs.existsSync(outputPath)){
    fs.mkdirSync(outputPath, {recursive: true})
}

const executeCpp = (filepath) =>{
    // [bc27bd23-dc8d-479c-babb-2b5b51e4d8ce, cpp]
    const jobId = path.basename(filepath).split(".")[0]
    const outPath = path.join(outputPath, `${jobId}.exe`)

    const promise1 = new Promise((resolve, reject) => {
        exec(`g++ ${filepath} -o ${outPath} && cd ${outputPath} && .\\${jobId}.exe`, 
                (error, stdout, stderr) => {
                    if(error){
                        //reject({error,stderr})
                    }
                    if(stderr){
                        reject(stderr)
                    }
                    resolve(stdout)
                })
    })

    promise1.catch((error) => {
        console.error(error)
    })
    
    return promise1
}


module.exports = {
    executeCpp
}