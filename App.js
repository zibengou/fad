import React from 'react';
import { Icon } from 'react-native-elements'
import { Text, View } from 'react-native';
import { TabNavigator } from 'react-navigation';

export default class App extends React.Component {
  render() {
    return <RootTab />;
  }
}

class HotScreen extends React.Component {
  render() {
    return (
      <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
        <Text>Hot!</Text>
      </View>
    );
  }
}

class FollowScreen extends React.Component {
  render() {
    return (
      <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
        <Text>Follow!</Text>
      </View>
    );
  }
}

class AccountScreen extends React.Component {
  render() {
    return (
      <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
        <Text>Account！</Text>
      </View>
    );
  }
}

const RootTab =  TabNavigator({
  hot: {
    screen: HotScreen,
    path: '/hot',
    navigationOptions: {
      tabBarLabel: '热门',
      tabBarIcon: ({ tintColor, focused }) => (
        <Icon
          name='whatshot'
          size={30}
          type="MaterialIcons"
          color={tintColor}
        />
      ),
    }
  },
  follow: {
    screen: FollowScreen,
    path: '/follow',
    navigationOptions: {
      tabBarLabel: '关注',
      tabBarIcon: ({ tintColor, focused }) => (
        <Icon
          name='eye'
          size={30}
          type="octicon"
          color={tintColor}
        />
      ),
    }
  },
  account: {
    screen: AccountScreen,
    path: '/account',
    navigationOptions: {
      tabBarLabel: '我的',
      tabBarIcon: ({ tintColor, focused }) => (
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