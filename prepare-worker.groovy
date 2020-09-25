#version1
node {
    stage("Initialize") {
        withCredentials([sshUserPrivateKey(credentialsId: 'yourSSHIDhere', keyFileVariable: 'SSHKEY', passphraseVariable: '', usernameVariable: 'SSHUSERNAME')]) {
            sh "ssh -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@yourIphere yum install epel-release -y "
        }
    }
}

#version2

properties([
    parameters([
        string(defaultValue: '', description: 'Input node IP', name: 'SSHNODE', trim: true)
        ])
    ])

node {
    stage("Initialize") {
        withCredentials([sshUserPrivateKey(credentialsId: 'yourSSHIDhere', keyFileVariable: 'SSHKEY', passphraseVariable: '', usernameVariable: 'SSHUSERNAME')]) {
            sh "ssh -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@$params.SSHNODE yum install epel-release -y "
        }
    }
}

#version3
properties([
    parameters([
        string(defaultValue: '', description: 'Input node IP', name: 'SSHNODE', trim: true)
        ])
    ])

node {
    withCredentials([sshUserPrivateKey(credentialsId: 'Jenkins-master-ssh', keyFileVariable: 'SSHKEY', passphraseVariable: '', usernameVariable: 'SSHUSERNAME')]) {
        stage("Initialize") {
            sh "ssh -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@${ params.SSHNODE } yum install epel-release -y "
        }
        stage("Install java") {
            sh "ssh -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@${ params.SSHNODE } yum install java-1.8.0-openjdk-devel -y"
            
        }
        stage("Install git") {
            sh "ssh -o StrictHostKeyChecking=no -i $SSHKEY $SSHUSERNAME@${ params.SSHNODE } yum install git -y"
        }
    }
}
