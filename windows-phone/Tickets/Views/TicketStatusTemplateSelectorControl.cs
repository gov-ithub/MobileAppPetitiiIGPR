using System.Windows;
using Tickets.Models;

namespace Tickets.Views
{
    public class TicketStatusTemplateSelectorControl : TemplateSelectorControl
    {
        public static readonly DependencyProperty PendingTemplateProperty = DependencyProperty.Register("PendingTemplate", typeof(DataTemplate), typeof(TicketStatusTemplateSelectorControl), new PropertyMetadata(default(DataTemplate)));
        public static readonly DependencyProperty UnansweredTemplateProperty = DependencyProperty.Register("UnansweredTemplate", typeof(DataTemplate), typeof(TicketStatusTemplateSelectorControl), new PropertyMetadata(default(DataTemplate)));
        public static readonly DependencyProperty AnsweredTemplateProperty = DependencyProperty.Register("AnsweredTemplate", typeof(DataTemplate), typeof(TicketStatusTemplateSelectorControl), new PropertyMetadata(default(DataTemplate)));

        public DataTemplate PendingTemplate
        {
            get { return (DataTemplate)GetValue(PendingTemplateProperty); }
            set { SetValue(PendingTemplateProperty, value); }
        }

        public DataTemplate UnansweredTemplate
        {
            get { return (DataTemplate)GetValue(UnansweredTemplateProperty); }
            set { SetValue(UnansweredTemplateProperty, value); }
        }

        public DataTemplate AnsweredTemplate
        {
            get { return (DataTemplate)GetValue(AnsweredTemplateProperty); }
            set { SetValue(AnsweredTemplateProperty, value); }
        }

        public override DataTemplate SelectTemplate(object item, DependencyObject container)
        {
            var ticket = item as Ticket;
            if (ticket != null)
                return GetTemplateForTicket(ticket);
            return base.SelectTemplate(item, container);
        }

        private DataTemplate GetTemplateForTicket(Ticket ticket)
        {
            if (!ticket.IsAnswered.HasValue)
                return PendingTemplate;

            return ticket.IsAnswered.Value ? AnsweredTemplate : UnansweredTemplate;
        }
    }
}
