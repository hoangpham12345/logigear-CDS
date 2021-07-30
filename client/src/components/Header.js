import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';

import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';


const styles = {
    root: {
        flexGrow: 1,
    },
    grow: {
        flexGrow: 1,
    },
    menuButton: {
        marginLeft: -12,
        marginRight: 20,
    },
    
};

function Header(props) {
    const { classes } = props;
    return (
        <Grid container component="main" className={classes.root}>
            <CssBaseline />
            <Grid className="App" component={Paper} style={{height: '100%', width: '100%', border: 0, boxShadow: '0 3px 5px 2px rgba(237, 228, 231, .5)' }}>
                <img src="img/mowede.png" style={{ height: '60px' }} />
            </Grid>
        </Grid>
            );
}

            Header.propTypes = {
                classes: PropTypes.object.isRequired,
};

            export default withStyles(styles)(Header);


// export default function Header() {
//     const classes = useStyles();


//     return (
//         <Grid className={classes.root}>
//             <Grid position="static" component={Paper} style={{ height: '10%', width: '100%', border: 0, boxShadow: '0 3px 5px 2px rgba(237, 228, 231, .5)' }}>
//                 <img src="img/mowede.png" style={{ height: '100%' }} />
//             </Grid>
//         </Grid>
//     );
// };
