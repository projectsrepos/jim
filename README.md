Jim is a Java instant messaging (IM) server designed for mobile, it supports chat and push messages base on the MQTT protocol. 

<p><b>MQTT Protocol</b></p>
MQTT is an extremely simple and lightweight messaging protocol. The publish/subscribe architecture is designed to make it easy to build loosely-coupled HTML5, native, and hybrid mobile applications. MQTT is ideal for use in constrained environments where network bandwidth is low, or where there is high latency, and with remote devices that might have limited processing capabilities and memory.

MQTT minimizes network bandwidth and device resource requirements when attempting to ensure reliability and delivery. This approach makes the MQTT protocol particularly well-suited for your business applications to interact with remote devices, such as mobile phones.

The MQTT protocol enables you to extend connectivity beyond your enterprise boundaries to smart devices. The protocol is efficient on battery, processor, and network bandwidth in mobile scenarios.

The MQTT protocol includes the following highlights:

 	Open and no-charge for easy adoption. MQTT is open to make it easy to adopt and adapt for the wide variety of devices, platforms, and operating systems that are used at the edge of a network.
 	A publish/subscribe messaging model that facilitates one-to-many distribution.
 	Ideal for constrained networks (low bandwidth, high latency, data limits, and fragile connections). MQTT message headers are kept as small as possible. The fixed header is just two bytes, and its on-demand, push-style message distribution keeps network use low.
 	Multiple service levels allow flexibility in handling different types of messages. Developers can designate that messages will be delivered at most one time, at least once, or exactly once.
 	Designed specifically for remote devices with little memory or processing power. Minimal headers, a small client footprint, and limited reliance on libraries make MQTT ideal for constrained devices.
 	Easy to use and implement, with a simple set of command messages. Many applications of MQTT can be accomplished using just the CONNECT, PUBLISH, SUBSCRIBE, and DISCONNECT commands.
 	Built-in support for loss of contact between client and server. The server is informed when a client connection breaks abnormally, enabling the message to be resent, or preserved for later delivery.
MQTT was co-invented by IBM and Eurotech. It was designed specifically for sending data over networks where connectivity is intermittent or bandwidth is at a premium. The protocol is simple, making it extremely efficient for reliable publish/subscribe messaging. MQTT enables devices to open a connection, keep it open using little power, and receive events or commands using a small two-byte header.

The MQTT protocol is built upon several fundamental concepts, all aimed at assuring message delivery and keeping the messages themselves as lightweight as possible. The following list includes a few features of the MQTT protocol:

 	Publish/subscribe
The MQTT protocol is based on the principle of publishing messages and subscribing to topics, which is typically referred to as a publish/subscribe model. Each message is published to a specific named topic. Clients who want to receive messages can make subscriptions against the topics that interest them.

 	Topics and subscriptions
Messages in MQTT are published to topics, which can be thought of as subject areas. Clients, in turn, sign up to receive particular messages by subscribing to a topic. Subscriptions can be explicit, which limits the messages that are received to the specific topic at hand, or you can use wildcard designators, such as a number sign (#) to receive messages for a variety of related topics.

For example, topic strings might include scores/football and scores/cricket, where a subscriber uses the score number to receive scores of all sports.

 	Quality of service (QoS) levels
MQTT defines three QoS levels for message delivery:

    QoS 0: At most once
    QoS 1: At least once
    QoS 2: exactly once
Each level designates a higher level of effort by the server to ensure that the message gets delivered. Higher QoS levels ensure more reliable message delivery, but might use more network bandwidth.

 	Retained messages
Retained  messages is an MQTT feature where the server keeps the messages and delivers them to future subscribing clients. A retained message enables a client to connect and receive the retained message as soon as it creates the subscription, without waiting for a new publication.

 	Clean sessions and durable connections
There are two types of MQTT clients:

    Those which the server remembers when they disconnect
    Those which the server does not remember
When an MQTT client connects to the server, the client indicates which type of client to use by setting the clean session flag. If the clean session flag is set to true, all of the client's subscriptions are removed when the client disconnects from the server.

If the flag is set to false, the connection is treated as durable, and the client's subscriptions remain in effect after any disconnection. In this event, subsequent messages that arrive carrying a high QoS designation are stored for delivery after the connection is reestablished. Using the clean session flag is optional.

 	Will messages
When a client connects to a server, it can inform the server that it has a will message that is published to a specific topic or topics in the event of an unexpected disconnection. A will message is particularly useful in settings where system managers must know immediately when a remote sensor has lost contact with the network