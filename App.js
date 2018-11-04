import React from 'react';
import {Icon} from 'react-native-elements'
import {TabNavigator} from 'react-navigation';
import HomePage from './src/page/HomePage';
import HotPage from './src/page/HotPage';
import AccountPage from './src/page/AccountPage';

export default class App extends React.Component {
    render() {
        return <RootTab/>;
    }
}

const RootTab = TabNavigator({
    home: {
        screen: HomePage,
        path: '/home',
        navigationOptions: {
            tabBarLabel: '首页',
            tabBarIcon: ({tintColor, focused}) => (
                <Icon
                    name='home'
                    size={30}
                    type="foundation"
                    color={tintColor}
                />
            ),
        }
    },
    hot: {
        screen: HotPage,
        path: '/hot',
        navigationOptions: {
            tabBarLabel: '热门',
            tabBarIcon: ({tintColor, focused}) => (
                <Icon
                    name='whatshot'
                    size={30}
                    type="MaterialIcons"
                    color={tintColor}
                />
            ),
        }
    },
    account: {
        screen: AccountPage,
        path: '/account',
        navigationOptions: {
            tabBarLabel: '我的',
            tabBarIcon: ({tintColor, focused}) => (
                <Icon
                    name='user'
                    size={30}
                    type="font-awesome"
                    color={tintColor}
                />
            ),
        }
    },
});