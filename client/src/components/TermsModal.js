import React from 'react'
import { makeStyles } from '@material-ui/core/styles';
import Modal from '@material-ui/core/Modal';

const useStyles = makeStyles((theme) => ({
  paper: {
    position: 'absolute',
    top: '20%',
    // left: '10%',
    margin: '0 auto',
    width: 400,
    height: 350,
    backgroundColor: theme.palette.background.paper,
    border: '2px solid #000',
    boxShadow: theme.shadows[5],
    padding: theme.spacing(2, 4, 3),
    overflow: 'auto',
    [theme.breakpoints.down('sm')]: {
      left: '10%',
    },
    [theme.breakpoints.down('md')]: {
      left: '25%',
    },
    [theme.breakpoints.up('lg')]: {
      left: '40%',
    }
  },
}));

const TermsModal = ({open, handleClose}) => {
  const classes = useStyles()

  return (
    <Modal
      open={open}
      onClose={handleClose}
      aria-labelledby="term-modal-title"
      aria-describedby="term-modal-description"
    >
      <div className={classes.paper}>
        <h1 style={{textAlign:'center'}}>Terms of Service</h1>
        <h2>1. Terms</h2>
        <p>By enter your credentials here, you are agree to be bound with this Website Terms of Service.</p>
        <h2>2. Data Collection</h2>
        <p>Your credentials will be stored in our database to verify you everytime you try to sign in to this Website.</p>
      </div>
    </Modal>
  )
}

export default TermsModal
