const { exec } = require("child_process")

const executePy = (filepath) =>{
    const promise1 = new Promise((resolve, reject) => {
        exec(`Python ${filepath}`, 
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
    executePy
}